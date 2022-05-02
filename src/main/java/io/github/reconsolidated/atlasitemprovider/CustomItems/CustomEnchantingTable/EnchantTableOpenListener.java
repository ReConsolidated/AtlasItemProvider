package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchantingTable;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchantTableOpenListener implements Listener {

    public EnchantTableOpenListener() {
        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }


    @EventHandler
    public void onOpenEnchantTable(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block != null && block.getType() == Material.ENCHANTING_TABLE) {
                event.setCancelled(true);
                new CustomEnchantingInventory(AtlasItemProvider.plugin, event.getPlayer());
            }
        }
    }



}
