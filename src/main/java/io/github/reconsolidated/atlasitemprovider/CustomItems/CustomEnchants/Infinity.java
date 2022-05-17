package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Infinity extends CustomEnchant {
    private static Infinity instance = null;


    public Infinity() {
        super("infinity", "Infinity", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Infinity Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, value);
    }

    public static Infinity getInstance() {
        return instance;
    }
}