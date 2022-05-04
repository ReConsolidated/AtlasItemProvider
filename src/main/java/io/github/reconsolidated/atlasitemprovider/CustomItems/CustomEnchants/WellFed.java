package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class WellFed extends CustomEnchant implements Listener {
    private static WellFed instance = null;


    public WellFed() {
        super("well_fed", ChatColor.YELLOW + "" + ChatColor.BOLD + "Well-Fed", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of WellFed Enchant (report this to developer)");
        }

        maxLevel = 1;

        setAcceptsChestplates(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onFoodChance(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null && item.getItemMeta() != null) {
                    if (get(item) > 0) {
                        event.setCancelled(true);
                        player.setFoodLevel(20);
                        return;
                    }
                }
            }
        }
    }


    public static WellFed getInstance() {
        return instance;
    }
}

