package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public abstract class ItemTrait {
    public abstract NamespacedKey getKey();

    private static Set<ItemTrait> traits = new HashSet<>();

    public static void initTraits() {
        traits.add(new Armor());
        traits.add(new BowDamage());
        traits.add(new CritChance());
        traits.add(new Damage());
        traits.add(new Durability());
        traits.add(new HuntingLuck());
        traits.add(new Rank());
        traits.add(new ToolLuck());
    }

    public static Set<ItemTrait> getTraitsSet() {
        return traits;
    }


    public Double get(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.getPersistentDataContainer().get(getKey(), PersistentDataType.DOUBLE);
    }

    public void set(ItemStack item, double value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
    }



}
