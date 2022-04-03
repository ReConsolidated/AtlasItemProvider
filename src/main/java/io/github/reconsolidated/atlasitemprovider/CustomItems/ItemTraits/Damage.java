package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;

public class Damage {
    public static NamespacedKey getDamageKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_damage");
    }
}
