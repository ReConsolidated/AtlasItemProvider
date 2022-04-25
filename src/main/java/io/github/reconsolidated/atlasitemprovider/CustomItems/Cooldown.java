package io.github.reconsolidated.atlasitemprovider.CustomItems;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Cooldown {
    private static Cooldown instance = null;

    static {
        new Cooldown();
    }

    public Cooldown() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Cooldown class (contact the developer)");
        }
    }

    public void set(ItemStack item, Long time) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.LONG, System.currentTimeMillis() + time);
        item.setItemMeta(meta);
    }

    public boolean isOnCooldown(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        Long cooldown = meta.getPersistentDataContainer().get(getKey(), PersistentDataType.LONG);
        if (cooldown == null) return false;
        return cooldown <= System.currentTimeMillis();
    }

    private NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_cooldown");
    }

    public static Cooldown getInstance() {
        return instance;
    }
}
