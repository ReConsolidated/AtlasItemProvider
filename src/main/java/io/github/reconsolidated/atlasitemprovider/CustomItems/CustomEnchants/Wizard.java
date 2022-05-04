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

public class Wizard extends CustomEnchant implements Listener {
    private static Wizard instance = null;


    public Wizard() {
        super("wizard", ChatColor.YELLOW + "" + ChatColor.BOLD + "Wizard", Rarity.RARE);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Wizard Enchant (report this to developer)");
        }

        maxLevel = 4;

        setAcceptsPickaxes(true);
        setAcceptsAxes(true);
        setAcceptsHoes(true);


        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        int level = get(item);
        if (level > 0) {
            event.setExpToDrop((int) (event.getExpToDrop() * (1 + 0.4 * level)));
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player != null) {
            ItemStack item = player.getInventory().getItemInMainHand();
            int level = get(item);
            if (level > 0) {
                event.setDroppedExp((int) (event.getDroppedExp() * (1 + 0.4 * level)));
            }
        }
    }

    public static Wizard getInstance() {
        return instance;
    }
}
