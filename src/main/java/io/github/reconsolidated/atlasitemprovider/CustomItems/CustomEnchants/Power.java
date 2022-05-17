package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Power extends CustomEnchant {
    private static Power instance = null;


    public Power() {
        super("power", "Power", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Power Enchant (report this to developer)");
        }

        maxLevel = 5;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, value);
    }

    public static Power getInstance() {
        return instance;
    }
}

