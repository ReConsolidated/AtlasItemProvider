package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SweepingEdge extends CustomEnchant {
    private static SweepingEdge instance = null;


    public SweepingEdge() {
        super("sweeping_edge", ChatColor.YELLOW + "" + ChatColor.BOLD + "Sweeping Edge", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of SweepingEdge Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, value);
    }

    public static SweepingEdge getInstance() {
        return instance;
    }
}
