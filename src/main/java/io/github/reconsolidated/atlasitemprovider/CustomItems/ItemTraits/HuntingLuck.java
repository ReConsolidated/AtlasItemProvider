package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// Stats: Damage, Durability, Crit Chance, Hunting Luck
public class HuntingLuck implements Listener {

    private final List<Entity> entitiesToDropMore;


    public HuntingLuck() {
        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
        entitiesToDropMore = new ArrayList<>();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // two cases - entity damaged directly by player (sword, axe, etc..)
        // or entity damaged by a projectile from player (bow, crossbow etc...)

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) event.getEntity();
            if (le.getHealth() - event.getFinalDamage() <= 0) {

                if (event.getDamager() instanceof Player) {
                    Player player = (Player) event.getDamager();
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() == Material.AIR) return;
                    ItemMeta meta = item.getItemMeta();
                    Double huntingLuck = meta.getPersistentDataContainer().get(getHuntingLuckKey(), PersistentDataType.DOUBLE);
                    if (huntingLuck != null) {
                        Random random = new Random();
                        if (random.nextInt(100) < huntingLuck) {
                            addToHuntingLuckList(event.getEntity());
                        }
                    }
                }

                if (event.getDamager() instanceof Projectile) {
                    Projectile projectile = (Projectile) event.getDamager();
                    if (projectile.getShooter() instanceof Player) {
                        Double huntingLuck = projectile.getPersistentDataContainer().get(getHuntingLuckKey(), PersistentDataType.DOUBLE);
                        if (huntingLuck != null) {
                            Random random = new Random();
                            if (random.nextInt(100) < huntingLuck) {
                                addToHuntingLuckList(event.getEntity());
                            }
                        }
                    }
                }

            }
        }

    }

    private void addToHuntingLuckList(Entity entity) {
        entitiesToDropMore.add(entity);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (entitiesToDropMore.contains(event.getEntity())) {
            entitiesToDropMore.remove(event.getEntity());
            List<ItemStack> l = new ArrayList<>(event.getDrops());
            for (ItemStack i : l) {
                event.getDrops().add(i);
            }
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        ItemStack item = event.getBow();
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                Double hunterLuck = meta.getPersistentDataContainer().get(getHuntingLuckKey(), PersistentDataType.DOUBLE);
                if (hunterLuck != null) {
                    event.getProjectile().getPersistentDataContainer().set(getHuntingLuckKey(), PersistentDataType.DOUBLE, hunterLuck);
                }
            }
        }
    }

    public static NamespacedKey getHuntingLuckKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_hunting_luck");
    }

}
