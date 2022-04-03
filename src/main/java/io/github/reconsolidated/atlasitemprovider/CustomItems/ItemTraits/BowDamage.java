package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
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


// Stats: Damage, Durability, Crit Chance, Hunting Luck
public class BowDamage implements Listener {
    public BowDamage() {
        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Double bonusDamage = projectile.getPersistentDataContainer().get(getBowDamageKey(), PersistentDataType.DOUBLE);
                if (bonusDamage != null) {
                    event.setDamage(bonusDamage);
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
                Double damage = meta.getPersistentDataContainer().get(getBowDamageKey(), PersistentDataType.DOUBLE);
                if (damage != null) {
                    damage *= event.getForce();
                    event.getProjectile().getPersistentDataContainer().set(getBowDamageKey(), PersistentDataType.DOUBLE, damage);
                }
            }
        }
    }

    public static NamespacedKey getBowDamageKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_bow_damage");
    }

}
