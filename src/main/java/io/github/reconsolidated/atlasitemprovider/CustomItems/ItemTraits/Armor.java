package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Armor extends ItemTrait {
    private static Armor instance;

    public static Armor getInstance() {
        return instance;
    }

    public Armor() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of Armor ItemTrait (this is a bug, report this to the developer)");
        }
    }


    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_armor");
    }

}
