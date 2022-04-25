package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Antigravity extends CustomEnchant implements Listener {
    private static Antigravity instance = null;

    static {
        allEnchants.add(new Antigravity());
    }

    public Antigravity() {
        super("antigravity", ChatColor.YELLOW + "" + ChatColor.BOLD + "Antigravity");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Antigravity Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }

    @EventHandler
    public void stopFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                List<ItemStack> items = new ArrayList<>(List.of(player.getInventory().getArmorContents()));
                for (ItemStack item : items) {
                    if (get(item) > 0) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    public static Antigravity getInstance() {
        return instance;
    }
}
