package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Getaway extends CustomEnchant implements Listener {
    private static Getaway instance = null;


    public Getaway() {
        super("getaway", "Getaway", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Getaway Enchant (report this to developer)");
        }

        maxLevel = 1;

        setAcceptsBows(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }


    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            ItemStack bow = event.getBow();

            int level = get(bow);

            if (level > 0) {
                Player player = (Player) event.getEntity();
                if (event.getForce() > 0.4 && player.getLocation().getWorld().getBlockAt(player.getLocation().clone().add(0, -1, 0)).getType() != Material.AIR) {
                    player.setVelocity(player.getVelocity().add(new Vector(0, 3, 0)));
                }

            }
        }
    }


    public static Getaway getInstance() {
        return instance;
    }
}

