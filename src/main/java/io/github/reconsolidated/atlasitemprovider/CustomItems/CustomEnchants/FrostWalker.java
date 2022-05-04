package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class FrostWalker extends CustomEnchant {
    private static FrostWalker instance = null;


    public FrostWalker() {
        super("frost_walker", ChatColor.YELLOW + "" + ChatColor.BOLD + "Frost Walker", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of FrostWalker Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.FROST_WALKER, value);
    }

    public static FrostWalker getInstance() {
        return instance;
    }
}