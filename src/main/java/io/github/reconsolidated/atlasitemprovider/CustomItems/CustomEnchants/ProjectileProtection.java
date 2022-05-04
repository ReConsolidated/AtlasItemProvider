package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ProjectileProtection extends CustomEnchant {
    private static ProjectileProtection instance = null;


    public ProjectileProtection() {
        super("projectile_protection", ChatColor.YELLOW + "" + ChatColor.BOLD + "Projectile Protection", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of ProjectileProtection Enchant (report this to developer)");
        }

        maxLevel = 4;


    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, value);
    }

    public static ProjectileProtection getInstance() {
        return instance;
    }
}

