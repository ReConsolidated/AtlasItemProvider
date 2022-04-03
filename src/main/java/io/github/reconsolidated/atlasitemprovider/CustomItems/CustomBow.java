package io.github.reconsolidated.atlasitemprovider.CustomItems;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.CritChance;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Durability;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomBow {
    public static ItemStack createCustomBow(Material material, double damage, int maxDurability, double critChance, double huntingLuck) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(getBonusDamageKey(), PersistentDataType.DOUBLE, damage);
        meta.getPersistentDataContainer().set(Durability.getMaxDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(Durability.getDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(CritChance.getCritChanceKey(), PersistentDataType.DOUBLE, critChance);
        meta.getPersistentDataContainer().set(getHuntingLuckKey(), PersistentDataType.DOUBLE, huntingLuck);

        item.setItemMeta(meta);
        return item;
    }

    private static NamespacedKey getBonusDamageKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_bonus_damage");
    }

    private static NamespacedKey getHuntingLuckKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_hunting_luck");
    }

}
