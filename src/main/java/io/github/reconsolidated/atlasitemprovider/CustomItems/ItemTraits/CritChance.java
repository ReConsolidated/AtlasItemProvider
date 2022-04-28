package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;


// Stats: Damage, Durability, Crit Chance, Hunting Luck
public class CritChance extends ItemTrait implements Listener {
    private static CritChance instance;

    public static CritChance getInstance() {
        return instance;
    }


    public CritChance() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of CritChance ItemTrait (this is a bug, report this to the developer)");
        }



        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // two cases - entity damaged directly by player (sword, axe, etc..)
        // or entity damaged by a projectile from player (bow, crossbow etc...)

        // CASE 1 - DIRECT DAMAGE
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) return;
            Double critChance = get(item);
            if (critChance != null) {
                Random random = new Random();
                if (random.nextInt(100) < critChance) {
                    event.setDamage(event.getDamage() * 2);
                    player.sendActionBar(Component.text("CRIT!").color(TextColor.color(255, 0, 0)));
                }
            }
        }

        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();
                Double critChance = projectile.getPersistentDataContainer().get(getKey(), PersistentDataType.DOUBLE);
                if (critChance != null) {
                    Random random = new Random();
                    if (random.nextInt(100) < critChance) {
                        event.setDamage(event.getDamage() * 2);
                        player.sendActionBar(Component.text("CRIT!").color(TextColor.color(255, 0, 0)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        ItemStack item = event.getBow();
        if (item != null) {
            Double chance = get(item);
            if (chance != null) {
                event.getProjectile().getPersistentDataContainer().set(getKey(), PersistentDataType.DOUBLE, chance);
            }
        }
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_crit_chance");
    }
}
