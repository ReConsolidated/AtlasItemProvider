package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class CritChance implements Listener {
    public CritChance() {
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
            if (item == null || item.getType() == Material.AIR) return;
            ItemMeta meta = item.getItemMeta();
            Double critChance = meta.getPersistentDataContainer().get(getCritChanceKey(), PersistentDataType.DOUBLE);
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
                player.sendMessage(ChatColor.RED + "Projecitle hit");
                if (projectile.getPersistentDataContainer().get(getCritChanceKey(), PersistentDataType.DOUBLE) != null) {
                    player.sendMessage(ChatColor.RED + "WORKS D:D:D!!!");
                }
            }
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        ItemStack item = event.getBow();
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                Double chance = meta.getPersistentDataContainer().get(getCritChanceKey(), PersistentDataType.DOUBLE);
                if (chance != null) {
                    event.getProjectile().getPersistentDataContainer().set(getCritChanceKey(), PersistentDataType.DOUBLE, chance);
                }
            }
        }
    }

    public static NamespacedKey getCritChanceKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_crit_chance");
    }

}
