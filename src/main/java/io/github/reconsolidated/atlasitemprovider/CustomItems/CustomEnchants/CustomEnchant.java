package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomEnchant {
    protected static List<CustomEnchant> allEnchants = new ArrayList<>();

    private final String name;
    private final String displayName;

    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsSwords;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsPickaxes;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsAxes;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsBows;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsHoes;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsBoots;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsHelmets;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsArmors;

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

    public static Map<CustomEnchant, Integer> getEnchants(ItemStack item) {
        Map<CustomEnchant, Integer> result = new HashMap<>();
        for (NamespacedKey key : item.getItemMeta().getPersistentDataContainer().getKeys()) {
            for (CustomEnchant enchant : allEnchants) {
                if (enchant.getKey().equals(key)) {
                    result.put(enchant, enchant.get(item));
                }
            }
        }
        return result;
    }


    public String getDisplayName() {
        return displayName;
    }

    public boolean canBeAppliedTo(ItemStack item) {
        if (item.getType().toString().contains("SWORD")) {
            return acceptsSwords;
        }
        if (item.getType().toString().contains("PICKAXE")) {
            return acceptsPickaxes;
        }
        if (item.getType().toString().contains("AXE")) {
            return acceptsAxes;
        }
        if (item.getType() == Material.BOW) {
            return acceptsBows;
        }
        if (item.getType().toString().contains("HOE")) {
            return acceptsHoes;
        }
        if (item.getType().toString().contains("BOOTS")) {
            return acceptsBoots || acceptsArmors;
        }
        if (item.getType().toString().contains("HELMET")) {
            return acceptsHelmets || acceptsArmors;
        }
        if (item.getType().toString().contains("CHESTPLATE")) {
            return acceptsArmors;
        }
        if (item.getType().toString().contains("LEGGINGS")) {
            return acceptsArmors;
        }
        return false;
    }
}
