package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Destruction extends CustomEnchant {
    private static Destruction instance = null;
    static {
        allEnchants.add(new Destruction());
    }

    public Destruction() {
        super("destruction", ChatColor.YELLOW + "" + ChatColor.BOLD + "Destruction");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Destruction Enchant (report this to developer)");
        }

  }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);

    }


    public static Destruction getInstance() {
        return instance;
    }
}
