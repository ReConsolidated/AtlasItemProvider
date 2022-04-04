package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;

public enum Trait {
    ARMOR, BOW_DAMAGE, CRIT_CHANCE, DAMAGE, DURABILITY, HUNTING_LUCK, TOOL_LUCK;

    public static NamespacedKey getTraitKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_trait");
    }
}
