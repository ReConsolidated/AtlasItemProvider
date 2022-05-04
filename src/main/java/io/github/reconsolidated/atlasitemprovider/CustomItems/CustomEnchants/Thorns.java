package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Thorns extends CustomEnchant {
    private static Thorns instance = null;


    public Thorns() {
        super("thorns", ChatColor.YELLOW + "" + ChatColor.BOLD + "Thorns", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Thorns Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.THORNS, value);
    }

    public static Thorns getInstance() {
        return instance;
    }
}

