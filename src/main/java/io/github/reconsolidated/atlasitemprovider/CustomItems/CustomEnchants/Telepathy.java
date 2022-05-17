package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Telepathy extends CustomEnchant implements Listener {
    private static Telepathy instance = null;


    public Telepathy() {
        super("telepathy", "Telepathy", Rarity.RARE);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Telepathy Enchant (report this to developer)");
        }

        maxLevel = 1;

        setAcceptsTools(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (get(item) > 0) {
            for (ItemStack drop : event.getBlock().getDrops(item, player)) {
               Map<Integer, ItemStack> left = player.getInventory().addItem(drop);
               if (left.size() == 0) {
                   event.getBlock().getDrops(item, player).remove(drop);
               }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player != null) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (get(item) > 0) {
                List<ItemStack> leftOvers = new ArrayList<>();
                for (ItemStack drop : event.getDrops()) {
                    Map<Integer, ItemStack> left = player.getInventory().addItem(drop);
                    leftOvers.addAll(left.values());
                }
                event.getDrops().clear();
                event.getDrops().addAll(leftOvers);
            }
        }
    }

    public static Telepathy getInstance() {
        return instance;
    }
}