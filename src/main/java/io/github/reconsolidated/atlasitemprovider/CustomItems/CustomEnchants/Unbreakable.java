package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Unbreakable extends CustomEnchant {
    private static Unbreakable instance = null;
    private final int BASE_CHANCE = 10;

    static {
        allEnchants.add(new Unbreakable());
    }

    public Unbreakable() {
        super("unbreakable", ChatColor.YELLOW + "" + ChatColor.BOLD + "Unbreakable");
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

