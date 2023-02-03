package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.economy;

public class Thief extends CustomEnchant implements Listener {
    private static Thief instance = null;


    public Thief() {
        super("thief", "Thief", Rarity.RARE);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Thief Enchant (report this to developer)");
        }

        maxLevel = 3;

        setAcceptsSwords(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player != null) {
            int level = get(player.getInventory().getItemInMainHand());
            if (level > 0) {
                int bonusCash = 100 * level;
                economy.depositPlayer(player, bonusCash);
                player.sendMessage(ChatColor.YELLOW + "Received bonus " + ChatColor.GOLD
                        + bonusCash + ChatColor.YELLOW + "$ from Thief Enchant!");
            }
        }
    }

    public static Thief getInstance() {
        return instance;
    }
}
