package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Rarity extends ItemTrait {
    private static Rarity instance;

    public static Rarity getInstance() {
        return instance;
    }

    public Rarity() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of Rarity ItemTrait (this is a bug, report this to the developer)");
        }
    }

    public io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity getRarity(ItemStack item) {
        double value = get(item);
        return io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity.values()[(int) value];
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_rarity");
    }
}
