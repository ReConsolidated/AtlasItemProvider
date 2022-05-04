package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class FireAspect extends CustomEnchant {
    private static FireAspect instance = null;


    public FireAspect() {
        super("fire_aspect", ChatColor.YELLOW + "" + ChatColor.BOLD + "Fire Aspect", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of FireAspect Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, value);
    }

    public static FireAspect getInstance() {
        return instance;
    }
}