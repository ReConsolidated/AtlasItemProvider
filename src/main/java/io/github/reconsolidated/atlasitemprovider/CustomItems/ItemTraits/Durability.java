package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Durability extends ItemTrait implements Listener {
    private static Durability instance;

    public static Durability getInstance() {
        return instance;
    }


    public Durability() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of Durability ItemTrait (this is a bug, report this to the developer)");
        }
        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onItemUse(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            Double maxDurability = meta.getPersistentDataContainer().get(getMaxDurabilityKey(), PersistentDataType.DOUBLE);
            Integer currentDurability = meta.getPersistentDataContainer().get(getDurabilityKey(), PersistentDataType.INTEGER);
            if (currentDurability == null || maxDurability == null || maxDurability == 0) {
                return;
            }
            currentDurability -= event.getDamage();
            meta.getPersistentDataContainer().set(getDurabilityKey(), PersistentDataType.INTEGER, currentDurability);

            if (meta instanceof Damageable) {
                Damageable dmg = (Damageable) meta;
                dmg.getDamage();
                int itemMaxDurability = item.getType().getMaxDurability();
                int itemCurrentDurability = currentDurability * itemMaxDurability/(int) (double) maxDurability ;
                dmg.setDamage(itemMaxDurability - itemCurrentDurability);
            }

            item.setItemMeta(meta);
            meta.lore(LoreProvider.getLore(item));
            item.setItemMeta(meta);
        }
    }

    public static NamespacedKey getMaxDurabilityKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "max_item_durability");
    }


    public static NamespacedKey getDurabilityKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "current_item_durability");
    }

    @Override
    public NamespacedKey getKey() {
        return getMaxDurabilityKey();
    }

    public int getCurrent(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;
        Integer value = meta.getPersistentDataContainer().get(getDurabilityKey(), PersistentDataType.INTEGER);
        if (value == null) return 0;
        return value;
    }

    public ItemStack setCurrent(ItemStack item, int value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.getPersistentDataContainer().set(getDurabilityKey(), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
        return item;
    }
}
