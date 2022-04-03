package io.github.reconsolidated.atlasitemprovider;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorHelper {
    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

    public static String translate(String text) {
        Matcher match = pattern.matcher(text);

        while (match.find()) {
            String color = text.substring(match.start(), match.end());
            text = text.replace(color, ChatColor.of(color.substring(1)) + "");
            match = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
