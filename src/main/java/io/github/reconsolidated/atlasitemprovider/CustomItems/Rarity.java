package io.github.reconsolidated.atlasitemprovider.CustomItems;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY;

    public String getDisplayName() {
        return switch (this) {
            case COMMON -> ChatColor.WHITE + "COMMON";
            case UNCOMMON -> ChatColor.GREEN + "UNCOMMON";
            case RARE -> ChatColor.AQUA + "RARE";
            case EPIC -> ChatColor.RED + "EPIC";
            case LEGENDARY -> ChatColor.GOLD + "LEGENDARY";
        };
    }

    public String getChatStyle() {
        return switch (this) {
            case COMMON -> ChatColor.WHITE + "";
            case UNCOMMON -> ChatColor.GREEN + "";
            case RARE -> ChatColor.AQUA + "";
            case EPIC -> ChatColor.RED + "";
            case LEGENDARY -> ChatColor.GOLD + "" + ChatColor.BOLD;
        };
    }
}
