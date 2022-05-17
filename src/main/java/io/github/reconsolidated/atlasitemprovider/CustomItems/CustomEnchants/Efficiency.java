package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Efficiency extends CustomEnchant {
    private static Efficiency instance = null;


    public Efficiency() {
        super("efficiency", "Efficiency", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Efficiency Enchant (report this to developer)");
        }

        maxLevel = 5;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, value);
    }

    public static Efficiency getInstance() {
        return instance;
    }
}
