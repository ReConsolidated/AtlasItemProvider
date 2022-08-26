package io.github.reconsolidated.atlasitemprovider.Utils;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.TreeMap;

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

    public static String tr(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getDisplayName(ItemStack item) {
        if (item == null) return null;
        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();
            TextComponent displayName = (TextComponent) meta.displayName();
            if (displayName != null) {
                return displayName.content();
            }
        }

        return item.getType().toString().replace("_", " ");
    }


    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }
}
