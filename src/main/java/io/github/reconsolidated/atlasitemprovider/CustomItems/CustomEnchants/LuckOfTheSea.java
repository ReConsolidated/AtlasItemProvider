package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LuckOfTheSea extends CustomEnchant {
    private static LuckOfTheSea instance = null;


    public LuckOfTheSea() {
        super("luck_of_the_sea", "Luck of the Sea", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of LuckOfTheSea Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.LURE, value);
    }

    public static LuckOfTheSea getInstance() {
        return instance;
    }
}
