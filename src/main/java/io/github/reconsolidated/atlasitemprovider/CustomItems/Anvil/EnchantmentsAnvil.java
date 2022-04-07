package io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EnchantmentsAnvil implements Listener {

    @EventHandler
    public void onAnvilUse(InventoryClickEvent event) {
        if (event.getClickedInventory() == null
                || event.getClickedInventory().getType() != InventoryType.ANVIL) return;

        AnvilInventory inventory = (AnvilInventory) event.getClickedInventory();
        ItemStack clicked = inventory.getItem(event.getSlot());
        if (clicked != null && clicked.equals(inventory.getResult())) {
            if (event.getClick().isLeftClick()) {
                Random random = new Random();
                if (random.nextInt(100) < 50) {
                    event.setCancelled(true);
                    event.getWhoClicked().setItemOnCursor(inventory.getFirstItem());
                    inventory.setFirstItem(new ItemStack(Material.AIR));
                    inventory.setSecondItem(new ItemStack(Material.AIR));
                    event.getWhoClicked().sendMessage("Lucky");
                } else {
                    event.getWhoClicked().sendMessage("Unlucky");
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}
