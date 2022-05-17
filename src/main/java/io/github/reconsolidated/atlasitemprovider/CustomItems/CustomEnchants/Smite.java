package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Smite extends CustomEnchant {
    private static Smite instance = null;


    public Smite() {
        super("smite", "Smite", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Smite Enchant (report this to developer)");
        }

        maxLevel = 3;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, value);
    }

    public static Smite getInstance() {
        return instance;
    }
}


