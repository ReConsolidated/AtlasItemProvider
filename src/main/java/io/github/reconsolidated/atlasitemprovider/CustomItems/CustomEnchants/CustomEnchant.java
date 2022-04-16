package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomEnchant {
    protected static List<CustomEnchant> allEnchants = new ArrayList<>();

    private final String name;
    private final String displayName;

    public CustomEnchant(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public void set(ItemStack item, int value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
        meta.lore(LoreProvider.getLore(item));
        item.setItemMeta(meta);
    }

    public Integer get(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(getKey(), PersistentDataType.INTEGER);
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "enchant_" + name);
    }

    public String getName() {
        return name;
    }

    public static CustomEnchant getEnchant(String name) {
        for (CustomEnchant enchant : allEnchants) {
            if (enchant.getName().equals(name)) {
                return enchant;
            }
        }
        return null;
    }

    public static List<CustomEnchant> getEnchants(ItemStack item) {
        List<CustomEnchant> result = new ArrayList<>();
        for (NamespacedKey key : item.getItemMeta().getPersistentDataContainer().getKeys()) {
            for (CustomEnchant enchant : allEnchants) {
                if (enchant.getKey().equals(key)) {
                    result.add(enchant);
                }
            }
        }
        return result;
    }


    public String getDisplayName() {
        return displayName;
    }
}
