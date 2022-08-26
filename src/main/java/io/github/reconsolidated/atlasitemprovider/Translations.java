package io.github.reconsolidated.atlasitemprovider;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import io.github.reconsolidated.atlasitemprovider.ConfigUtils.CustomConfig;
import java.lang.reflect.Field;

public class Translations {
    public static String OIL_DRILL_NOT_OWNED = "§cYou do not own this oil drill.";
    public static String ON_DRILL_UPGRADED = "§aDrill upgraded!";

    public static void loadTranslations() {
        String translationsFileName = AtlasItemProvider.plugin.getConfig().getString("translations_file", "translations.yml");
        CustomConfig translationsConfig = new CustomConfig(translationsFileName);
        FileConfiguration config = translationsConfig.get();
        for (Field field : Translations.class.getDeclaredFields()) {
            if (field.getType() == String.class) {
                try {
                    if (config.contains(field.getName())) {
                        field.set(null, tr(config.getString(field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String tr(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    public static void createDefaultTranslationsFile() {
        CustomConfig config = new CustomConfig("default_messages.yml");
        FileConfiguration configFile = config.get();
        for (Field field : Translations.class.getDeclaredFields()) {
            if (field.getType() == String.class) {
                try {
                    configFile.set(field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        config.save();
    }
}
