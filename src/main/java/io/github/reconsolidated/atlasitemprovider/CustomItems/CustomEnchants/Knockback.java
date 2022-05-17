package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Knockback extends CustomEnchant {
    private static Knockback instance = null;


    public Knockback() {
        super("knockback", "Knockback", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Knockback Enchant (report this to developer)");
        }

        maxLevel = 2;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, value);
    }

    public static Knockback getInstance() {
        return instance;
    }
}

