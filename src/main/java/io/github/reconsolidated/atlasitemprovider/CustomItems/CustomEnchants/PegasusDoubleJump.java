package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PegasusDoubleJump extends CustomEnchant implements Listener {
    private static PegasusDoubleJump instance = null;


    public PegasusDoubleJump() {
        super("pegasus_double_jump", "Pegasus Double Jump", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of PegasusDoubleJump Enchant (report this to developer)");
        }

        setAcceptsAxes(true);
        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    public void setFly(PlayerJoinEvent e) {
        e.getPlayer().setAllowFlight(true);
        e.getPlayer().setFlying(false);
    }

    @EventHandler
    public void setVelocity(PlayerToggleFlightEvent e) {

        Player p = e.getPlayer();

        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.isFlying()) {
            return;
        } else {
            e.setCancelled(true);
            if (get(p.getInventory().getBoots()) > 0) {
                p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).setY(1));
            }
        }

    }

    public static PegasusDoubleJump getInstance() {
        return instance;
    }
}
