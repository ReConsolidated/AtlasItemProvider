package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Unbreakable extends CustomEnchant {
    private static Unbreakable instance = null;


    public Unbreakable() {
        super("unbreakable", "Unbreakable", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Unbreakable Enchant (report this to developer)");
        }
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
    }

    public static Unbreakable getInstance() {
        return instance;
    }
}

