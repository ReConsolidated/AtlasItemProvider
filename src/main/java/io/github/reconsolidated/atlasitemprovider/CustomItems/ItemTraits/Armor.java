package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;

public class Armor {
    public static NamespacedKey getArmorKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_armor");
    }
}
