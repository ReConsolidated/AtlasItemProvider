package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;

public class ToolLuck {
    public static NamespacedKey getToolLuckKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_tool_luck");
    }
}
