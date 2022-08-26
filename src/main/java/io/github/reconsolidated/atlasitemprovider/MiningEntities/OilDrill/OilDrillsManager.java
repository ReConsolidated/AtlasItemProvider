package io.github.reconsolidated.atlasitemprovider.MiningEntities.OilDrill;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class OilDrillsManager implements Listener {

    public OilDrillsManager() {
        Bukkit.getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!isOilDrill(event.getRightClicked())) {
            return;
        }
        OilDrill drill = new OilDrill(event.getRightClicked());
        drill.interact(event.getPlayer());
    }

    private boolean isOilDrill(Entity entity) {
        return entity.getPersistentDataContainer().get(OilDrill.levelKey, PersistentDataType.INTEGER) != null;
    }
}
