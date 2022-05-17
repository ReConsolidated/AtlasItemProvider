package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class FireProtection extends CustomEnchant {
    private static FireProtection instance = null;


    public FireProtection() {
        super("fire_protection", "Fire Protection", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of FireProtection Enchant (report this to developer)");
        }

        maxLevel = 4;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, value);
    }

    public static FireProtection getInstance() {
        return instance;
    }
}
