package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;

public class ToolLuck extends ItemTrait {
    private static ToolLuck instance;

    public static ToolLuck getInstance() {
        return instance;
    }

    public ToolLuck() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of ToolLuck ItemTrait (this is a bug, report this to the developer)");
        }
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_tool_luck");
    }
}
