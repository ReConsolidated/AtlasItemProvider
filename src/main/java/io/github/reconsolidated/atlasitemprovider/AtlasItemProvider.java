package io.github.reconsolidated.atlasitemprovider;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import dev.esophose.playerparticles.particles.FixedParticleEffect;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil.EnchantmentsAnvil;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith.BlacksmithCommand;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchantingTable.EnchantTableOpenListener;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.*;
import io.github.reconsolidated.atlasitemprovider.CustomItems.MysteryEnchantedBook.MysteryBookManager;
import io.github.reconsolidated.atlasitemprovider.MiningEntities.OilDrill.OilDrillsManager;
import io.github.reconsolidated.atlasitemprovider.Particles.Styles.ParticleEffect;
import io.github.reconsolidated.atlasitemprovider.databaseConnection.ConnectionFactory;
import io.github.reconsolidated.atlasitemprovider.databaseConnection.MySQLConnectionFactory;
import io.github.reconsolidated.atlasitemprovider.rankedItems.RankedItemsService;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class AtlasItemProvider extends JavaPlugin  {
    public static AtlasItemProvider plugin;
    public static PlayerParticlesAPI ppAPI;
    public static Economy economy = null;
    @Getter
    private RankedItemsService rankedItemsService;

    @Getter
    private final ConnectionFactory connectionFactory = new MySQLConnectionFactory();

    static {
        ConfigurationSerialization.registerClass(ProviderItem.class, "provideritem");
    }

    private File dataFolder;
    private NamespacedKey nameKey;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        ppAPI = PlayerParticlesAPI.getInstance();

        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (FixedParticleEffect effect : ppAPI.getFixedParticleEffects(Bukkit.getConsoleSender())) {
                ppAPI.removeFixedEffect(Bukkit.getConsoleSender(), effect.getId());
            }
        }, 40L);

        nameKey = new NamespacedKey(this, "item_name");
        dataFolder = getDataFolder();

        rankedItemsService = new RankedItemsService();

        new ItemProviderCommand(this);

        ItemTrait.initTraits();
        CustomEnchant.init();
        new BlacksmithCommand();
        new EnchantTableOpenListener();
        new MysteryBookManager(this);
        new OilDrillsManager();

        getServer().getServicesManager().register(AtlasItemProvider.class, this, this, ServicePriority.Normal);
        Bukkit.getScheduler().runTaskLater(this, () -> {
            ExampleItems.init(this, this.getDataFolder());
        }, 100L);

        getServer().getPluginManager().registerEvents(new EnchantmentsAnvil(), this);

        Bukkit.getScheduler().runTaskTimer(this, ParticleEffect::tickParticles, 20L, 1L);

        Translations.createDefaultTranslationsFile();
        Translations.loadTranslations();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }




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
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(nameKey, PersistentDataType.STRING, name);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem(String category, String name) {
        return getItem(category, name, 1);
    }

    public String getItemName(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();
            if (displayName.length() > 0) {
                return displayName;
            }
        }
        String name = item.getItemMeta().getPersistentDataContainer().get(nameKey, PersistentDataType.STRING);
        if (name == null) {
            return item.getType().name().replaceAll("_", " ");
        }
        return name;
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
