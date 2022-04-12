package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class ItemTrait {
    protected static ItemTrait instance;
    public static ItemTrait getInstance() {
        return instance;
    }
    public abstract NamespacedKey getKey();


    public Integer getInteger(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.getPersistentDataContainer().get(getKey(), PersistentDataType.INTEGER);
    }

    public void setInteger(ItemStack item, Integer value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }


}
