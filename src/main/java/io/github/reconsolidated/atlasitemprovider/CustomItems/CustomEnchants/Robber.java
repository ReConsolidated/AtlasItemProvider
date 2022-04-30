package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.simplix.plugins.atlascoredata.AtlasCoreDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Robber extends CustomEnchant implements Listener {
    private static Robber instance = null;


    public Robber() {
        super("robber", ChatColor.YELLOW + "" + ChatColor.BOLD + "Robber");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Robber Enchant (report this to developer)");
        }

        setAcceptsSwords(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getPlayer().getKiller() == null) {
            return;
        }

        Player player = event.getPlayer().getKiller();

        int robberLevel = 0;
        List<ItemStack> list = new ArrayList<>(List.of(player.getInventory().getArmorContents()));
        list.add(player.getInventory().getItemInMainHand());

        for (ItemStack item : list) {
            robberLevel += get(item);
        }

        if (robberLevel > 0) {
            int min = 100 * robberLevel;
            int max = 1000 * robberLevel;

            Random random = new Random();
            int cash = random.nextInt(max-min+1) + min;

            AtlasCoreDataAPI.instance().playerDataStorage().addBalance(player.getUniqueId(), "currency", cash);
            player.sendMessage(ChatColor.YELLOW + "Received " + ChatColor.GOLD + ""
                    + cash + ChatColor.YELLOW + " from Robber Enchant.");
        }


    }


    public static Robber getInstance() {
        return instance;
    }
}
