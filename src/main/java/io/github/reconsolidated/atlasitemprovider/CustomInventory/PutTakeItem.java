package io.github.reconsolidated.atlasitemprovider.CustomInventory;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PutTakeItem extends InventoryItem{

    private Consumer<ItemStack> onStateChanged;

    public PutTakeItem(ItemStack item, int row, int column, Consumer<ItemStack> onStateChanged) {
        super(item, row, column);
        this.onStateChanged = onStateChanged;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getAction().equals(InventoryAction.PICKUP_ALL)) {
            onStateChanged.accept(null);
        }
        else if (event.getCurrentItem() != null) {

            onStateChanged.accept(event.getCurrentItem());
        } else if (event.getCursor() != null) {
            onStateChanged.accept(event.getCursor());
        }
    }
}

