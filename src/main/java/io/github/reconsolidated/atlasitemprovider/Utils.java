package io.github.reconsolidated.atlasitemprovider;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

    public static boolean hasStorageSpace(Inventory inventory) {
        return getStorageSpaceInInventory(inventory) > 0;
    }

    public static int getStorageSpaceInInventory(Inventory inventory) {
        int space = 0;
        for (ItemStack item : inventory.getStorageContents()) {
            if (item == null || item.getType().equals(Material.AIR)) {
                space++;
            }
        }
        return space;
    }

    public static String getDisplayName(ItemStack item) {
        if (item == null) return null;
        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta.displayName() != null) {
                TextComponent displayName = (TextComponent) meta.displayName();
                return displayName.content();
            }
        }

        return item.getType().toString().replace("_", " ");
    }
}
