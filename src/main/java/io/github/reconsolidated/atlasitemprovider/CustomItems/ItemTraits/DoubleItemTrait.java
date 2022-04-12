package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class DoubleItemTrait extends ItemTrait {
    public static DoubleItemTrait getInstance() {
        return (DoubleItemTrait) instance;
    }

    public void setDouble(ItemStack item, Double value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
    }

    public Double getDouble(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.getPersistentDataContainer().get(getKey(), PersistentDataType.DOUBLE);
    }
}
