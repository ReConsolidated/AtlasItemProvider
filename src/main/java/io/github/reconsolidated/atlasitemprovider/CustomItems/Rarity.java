package io.github.reconsolidated.atlasitemprovider.CustomItems;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY;

    public String getDisplayName() {
        return switch (this) {
            case COMMON -> ChatColor.WHITE + "COMMON";
            case UNCOMMON -> ChatColor.AQUA + "UNCOMMON";
            case RARE -> ChatColor.GREEN + "RARE";
            case EPIC -> ChatColor.RED + "EPIC";
            case LEGENDARY -> ChatColor.GOLD + "LEGENDARY";
        };
    }
}
