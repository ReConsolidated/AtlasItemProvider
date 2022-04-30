package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Forge extends CustomEnchant implements Listener {
    private static Forge instance = null;
    private static final Map<Material, Material> oreToIngotMap = new HashMap<>();

    static {
        oreToIngotMap.put(Material.IRON_ORE, Material.IRON_INGOT);
        oreToIngotMap.put(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT);
        oreToIngotMap.put(Material.GOLD_ORE, Material.GOLD_INGOT);
        oreToIngotMap.put(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT);
        oreToIngotMap.put(Material.COPPER_ORE, Material.COPPER_INGOT);
        oreToIngotMap.put(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT);
        oreToIngotMap.put(Material.DIAMOND_ORE, Material.DIAMOND);
        oreToIngotMap.put(Material.DEEPSLATE_DIAMOND_ORE, Material.DIAMOND);
        oreToIngotMap.put(Material.EMERALD_ORE, Material.DIAMOND);

    }

    public Forge() {
        super("forge", ChatColor.YELLOW + "" + ChatColor.BOLD + "Forge");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of StrongWilled Enchant (report this to developer)");
        }

        setAcceptsPickaxes(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onMine(BlockBreakEvent event) {

    }


    public static Forge getInstance() {
        return instance;
    }
}