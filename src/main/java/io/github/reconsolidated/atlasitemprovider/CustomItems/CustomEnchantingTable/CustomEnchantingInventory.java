package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchantingTable;

import io.github.reconsolidated.atlasitemprovider.CustomInventory.InventoryMenu;
import io.github.reconsolidated.atlasitemprovider.CustomInventory.PutTakeItem;
import io.github.reconsolidated.atlasitemprovider.CustomInventory.TakeOnlyItem;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomEnchantingInventory extends InventoryMenu {

    private ItemStack slot1 = new ItemStack(Material.AIR);
    private ItemStack slot2 = new ItemStack(Material.AIR);
    private ItemStack slot3 = new ItemStack(Material.AIR);


    public CustomEnchantingInventory(Plugin plugin, Player player) {
        super(plugin, player, "Enchanting Table", 3);

        fillWithEmptyItems();

        setSlot1(new ItemStack(Material.AIR));
        setSlot2(new ItemStack(Material.AIR));

        addItem(new TakeOnlyItem(new ItemStack(Material.AIR), 2, 7, (event) -> {
            setSlot1(new ItemStack(Material.AIR));
            setSlot2(new ItemStack(Material.AIR));
        }));
    }

    private void prepareResult() {
        // first item set and second item empty -> result is just the copy of first item
        if (slot1 != null && (slot2 == null || slot2.getType().equals(Material.AIR))) {
            setSlot3(slot1.clone());
            return;
        }

        // first item empty and second item whatever -> no result
        if (slot1 == null || slot1.getType().equals(Material.AIR)) {
            setSlot3(new ItemStack(Material.AIR));
            return;
        }

        // first item set and second item set -> result is calculated
        Map<CustomEnchant, Integer> enchants = CustomEnchant.getEnchants(slot2);

        if (canBeApplied(enchants.keySet(), slot1)) {
            setSlot3(applyEnchants(slot1.clone(), enchants));
        }
    }

    private ItemStack applyEnchants(ItemStack item, Map<CustomEnchant, Integer> enchants) {
        for (CustomEnchant enchant : enchants.keySet()) {
            enchant.set(item, enchants.get(enchant));
        }
        return item;
    }

    private boolean canBeApplied(Set<CustomEnchant> enchants, ItemStack slot1) {
        for (CustomEnchant enchant : enchants) {
            if (enchant.get(slot1) > 0) {
                return false;
            }
            if (!enchant.canBeAppliedTo(slot1)) {
                return false;
            }
        }
        return true;
    }

    private void setSlot3(ItemStack item) {
        addItem(new TakeOnlyItem(item, 2, 7, (event) -> {
            setSlot1(new ItemStack(Material.AIR));
            setSlot2(new ItemStack(Material.AIR));
        }));
    }

    private void setSlot2(ItemStack itemStack) {

        addItem(new PutTakeItem(itemStack, 2, 4, (item) -> {
            slot2 = item;
            prepareResult();
        }));
        slot2 = new ItemStack(Material.AIR);
    }

    private void setSlot1(ItemStack itemStack) {
        addItem(new PutTakeItem(itemStack, 2, 2, (item) -> {
            slot1 = item;
            prepareResult();
        }));

        slot1 = new ItemStack(Material.AIR);

    }
}