package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Durability implements Listener {

    public Durability() {
        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onItemUse(PlayerItemDamageEvent event) {
        Bukkit.broadcastMessage("Item damage event");
        ItemStack item = event.getItem();
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            Integer maxDurability = meta.getPersistentDataContainer().get(getMaxDurabilityKey(), PersistentDataType.INTEGER);
            Integer currentDurability = meta.getPersistentDataContainer().get(getDurabilityKey(), PersistentDataType.INTEGER);
            if (currentDurability == null || maxDurability == null || maxDurability == 0) {
                return;
            }
            currentDurability -= event.getDamage();
            meta.getPersistentDataContainer().set(getDurabilityKey(), PersistentDataType.INTEGER, currentDurability);
            Bukkit.broadcastMessage("damage: " + event.getDamage());
            Bukkit.broadcastMessage("new durability: " + currentDurability);

            if (meta instanceof Damageable) {
                Damageable dmg = (Damageable) meta;
                dmg.getDamage();
                int itemMaxDurability = item.getType().getMaxDurability();
                int itemCurrentDurability = currentDurability * itemMaxDurability/maxDurability ;
                Bukkit.broadcastMessage("item new durability: " + itemCurrentDurability);
                dmg.setDamage(itemMaxDurability - itemCurrentDurability);
            }

            item.setItemMeta(meta);
        }
    }

    public static NamespacedKey getMaxDurabilityKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "max_item_durability");
    }


    public static NamespacedKey getDurabilityKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "current_item_durability");
    }
}
