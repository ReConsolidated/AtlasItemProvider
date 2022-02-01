package io.github.reconsolidated.atlasitemprovider;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AtlasItemProvider extends JavaPlugin {
    private static File dataFolder;
    private static Plugin plugin;
    private static NamespacedKey nameKey;


    public static ItemStack getItem(String category, String name, int amount) {
        YamlConfiguration config = CustomConfig.loadCustomConfig(category, dataFolder, true);
        if (config.contains(name)) {
            return named(config.getItemStack(name), name);
        } else {
            Material material = Material.getMaterial(name);
            if (material != null) {
                return new ItemStack(material, amount);
            } else {
                return nameItem(new ItemStack(Material.DIRT, amount), Component.text("Item not found"));
            }
        }
    }

    private static ItemStack named(ItemStack item, String name) {
        item.getItemMeta().getPersistentDataContainer().set(nameKey, PersistentDataType.STRING, name);
        return item;
    }

    public static ItemStack getItem(String category, String name) {
        return getItem(category, name, 1);
    }

    public String getItemName(ItemStack item) {
        String name = item.getItemMeta().getPersistentDataContainer().get(nameKey, PersistentDataType.STRING);
        if (name == null) {
            return item.getType().name();
        }
        return name;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        dataFolder = getDataFolder();
        plugin = this;
        nameKey = new NamespacedKey(plugin, "name");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private static ItemStack nameItem(ItemStack item, Component displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName.style(Style.style().decoration(TextDecoration.ITALIC, false)));
        item.setItemMeta(meta);
        return item;
    }
}
