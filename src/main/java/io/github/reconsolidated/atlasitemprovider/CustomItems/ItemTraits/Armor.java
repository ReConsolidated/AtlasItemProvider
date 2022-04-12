package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Armor extends DoubleItemTrait {
    static {
        instance = new Armor();
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_armor");
    }

}
