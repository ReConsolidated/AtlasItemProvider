package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Riptide extends CustomEnchant {
    private static Riptide instance = null;


    public Riptide() {
        super("riptide", "Riptide", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Riptide Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.RIPTIDE, value);
    }

    public static Riptide getInstance() {
        return instance;
    }
}