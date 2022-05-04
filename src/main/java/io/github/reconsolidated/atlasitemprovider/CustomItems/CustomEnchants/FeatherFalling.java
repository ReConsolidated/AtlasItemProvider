package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class FeatherFalling extends CustomEnchant {
    private static FeatherFalling instance = null;


    public FeatherFalling() {
        super("feather_falling", ChatColor.YELLOW + "" + ChatColor.BOLD + "Feather Falling", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of FeatherFalling Enchant (report this to developer)");
        }

        maxLevel = 4;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, value);
    }

    public static FeatherFalling getInstance() {
        return instance;
    }
}
