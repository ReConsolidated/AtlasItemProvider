package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class AquaAffinity extends CustomEnchant {
    private static AquaAffinity instance = null;


    public AquaAffinity() {
        super("aqua_affinity", "Aqua Affinity", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of AquaAffinity Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        item.addUnsafeEnchantment(Enchantment.WATER_WORKER, value);
    }

    public static AquaAffinity getInstance() {
        return instance;
    }
}