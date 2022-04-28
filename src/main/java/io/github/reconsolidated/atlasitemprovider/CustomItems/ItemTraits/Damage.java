package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;

public class Damage extends ItemTrait {
    private static Damage instance;

    public static Damage getInstance() {
        return instance;
    }


    public Damage() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of Damage ItemTrait (this is a bug, report this to the developer)");
        }
    }


    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_damage");

    }
}
