package io.github.reconsolidated.atlasitemprovider.Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class PersistenceUtils {
    public static int getInt(PersistentDataHolder holder, NamespacedKey key) {
        Integer value = holder.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        if (value == null) {
            throw new IllegalStateException("No integer value found for key " + key);
        }
        return value;
    }

    public static String getString(PersistentDataHolder holder, NamespacedKey key) {
        return holder.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    public static void setInt(PersistentDataHolder holder, NamespacedKey key, int value) {
        holder.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
    }

    public static void setString(PersistentDataHolder holder, NamespacedKey key, String value) {
        holder.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
    }
}
