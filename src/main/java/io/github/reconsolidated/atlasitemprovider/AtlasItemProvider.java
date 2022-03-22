package io.github.reconsolidated.atlasitemprovider;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class AtlasItemProvider extends JavaPlugin  {
    static {
        ConfigurationSerialization.registerClass(ProviderItem.class, "provideritem");
    }

    private File dataFolder;
    private NamespacedKey nameKey;

    public ItemStack getItem(String category, String name, int amount) {
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

    public List<ItemStack> getAllFromCategory(String category) {

        YamlConfiguration config = CustomConfig.loadCustomConfig(category, dataFolder, true);
        List<ItemStack> items = new ArrayList<>();
        for (String key : config.getKeys(false)) {
            items.add(config.getItemStack(key));
        }
        return items;
    }

    private ItemStack named(ItemStack item, String name) {

        item.getItemMeta().getPersistentDataContainer().set(nameKey, PersistentDataType.STRING, name);
        return item;
    }

    public ItemStack getItem(String category, String name) {
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
        nameKey = new NamespacedKey(this, "item_name");
        dataFolder = getDataFolder();

        new ItemProviderCommand(this);
        getServer().getServicesManager().register(AtlasItemProvider.class, this, this, ServicePriority.Normal);
        ExampleItems.init(this, this.getDataFolder());
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

    public void addItem(ItemStack item, String name, String category) {
        YamlConfiguration config = CustomConfig.loadCustomConfig(category, dataFolder, true);
        assert config != null : "Couldn't load config: " + category;
        config.set(name, item);
        CustomConfig.saveCustomConfig(category, dataFolder, config);
    }
}
