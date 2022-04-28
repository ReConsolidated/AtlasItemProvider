package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.simplix.plugins.atlascoredata.AtlasCoreDataAPI;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Thief extends CustomEnchant implements Listener {
    private static Thief instance = null;

    static {
        allEnchants.add(new Thief());
    }

    public Thief() {
        super("thief", ChatColor.YELLOW + "" + ChatColor.BOLD + "Thief");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Thief Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player != null) {
            int level = get(player.getInventory().getItemInMainHand());
            if (level > 0) {
                int bonusCash = 100 * level;
                AtlasCoreDataAPI.instance().playerDataStorage().addBalance(player.getUniqueId(), "currency", bonusCash);
                player.sendMessage(ChatColor.YELLOW + "Received bonus " + ChatColor.GOLD
                        + bonusCash + ChatColor.YELLOW + "$ from Thief Enchant!");
            }
        }
    }

    public static Thief getInstance() {
        return instance;
    }
}