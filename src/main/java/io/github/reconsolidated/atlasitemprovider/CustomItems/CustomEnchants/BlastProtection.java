package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class BlastProtection extends CustomEnchant {
    private static BlastProtection instance = null;


    public BlastProtection() {
        super("blast_protection", ChatColor.YELLOW + "" + ChatColor.BOLD + "BlastProtection", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of BlastProtection Enchant (report this to developer)");
        }

        maxLevel = 5;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, value);
    }

    public static BlastProtection getInstance() {
        return instance;
    }
}
