package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Absorb extends CustomEnchant implements Listener {
    private static Absorb instance = null;

    public Absorb() {
        super("absorb", "Absorb", Rarity.RARE);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Absorb Enchant (report this to developer)");
        }

        maxLevel = 3;

        setAcceptsSwords(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();

            ItemStack item = player.getInventory().getItemInMainHand();
            int level = get(item);
            if (level == 1) {
                player.setHealth(Math.min(player.getHealth()*1.2, player.getMaxHealth()));
            }
            if (level == 2) {
                player.setHealth(Math.min(player.getHealth()*1.3, player.getMaxHealth()));
            }
            if (level == 3) {
                player.setHealth(Math.min(player.getHealth()*1.4, player.getMaxHealth()));
            }
        }
    }

    public static Absorb getInstance() {
        return instance;
    }
}


