package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchantingTable;

import io.github.reconsolidated.atlasitemprovider.CustomInventory.InventoryMenu;
import io.github.reconsolidated.atlasitemprovider.CustomInventory.PutTakeItem;
import io.github.reconsolidated.atlasitemprovider.CustomInventory.TakeOnlyItem;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

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
        setSlot3(new ItemStack(Material.AIR));
    }

    private void prepareResult() {
        // first item set and second item empty -> result is just the copy of first item
        Bukkit.broadcastMessage("Slot 1: " + slot1);
        Bukkit.broadcastMessage("Slot 2: " + slot2);
        Bukkit.broadcastMessage("Slot 3: " + slot3);


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

        Bukkit.broadcastMessage("Checking if enchant can be applied");
        if (canBeApplied(enchants.keySet(), slot1)) {
            Bukkit.broadcastMessage("It can");
            setSlot3(applyEnchants(slot1.clone(), enchants));
        }
    }

    private ItemStack applyEnchants(ItemStack item, Map<CustomEnchant, Integer> enchants) {
        for (CustomEnchant enchant : enchants.keySet()) {
            enchant.set(item, enchants.get(enchant));
        }
        ItemMeta meta = item.getItemMeta();
        meta.lore(LoreProvider.getLore(item));
        item.setItemMeta(meta);
        return item;
    }

    private boolean canBeApplied(Set<CustomEnchant> enchants, ItemStack slot1) {
        Set<CustomEnchant> itemEnchants = CustomEnchant.getEnchants(slot1).keySet();
        boolean alreadyHasLegendary = false;

        for (CustomEnchant e : itemEnchants) {
            if (e.getRarity() == Rarity.LEGENDARY) {
                alreadyHasLegendary = true;
                break;
            }
        }

        for (CustomEnchant enchant : enchants) {
            if (enchant.getRarity() == Rarity.LEGENDARY && alreadyHasLegendary) {
                return false;
            }
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
        slot2 = itemStack;
    }

    private void setSlot1(ItemStack itemStack) {
        addItem(new PutTakeItem(itemStack, 2, 2, (item) -> {
            if (item != null && item.getAmount() > 1) return;
            Bukkit.broadcastMessage("Setting slot 1 to " + item);
            if (item != null) {
                slot1 = item.clone();
            } else {
                slot1 = null;
            }

            prepareResult();
        }));

        Bukkit.broadcastMessage("Setting slot 1 to " + itemStack);
        slot1 = itemStack;

    }
}
