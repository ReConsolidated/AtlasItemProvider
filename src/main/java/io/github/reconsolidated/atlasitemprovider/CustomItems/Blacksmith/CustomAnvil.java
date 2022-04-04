package io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Trait;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomAnvil implements Listener {

    private InventoryView view;
    private Player player;

    private int amountUsed = 0;

    public CustomAnvil(Player player) {
        view = player.openSmithingTable(player.getLocation(), true);
        AtlasItemProvider.plugin.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onPrepareResult(PrepareSmithingEvent event) {
        if (!event.getView().equals(view)) return;

        if (event.getInventory().getInputEquipment() != null && event.getInventory().getInputMineral() != null) {
            if (isUpgradeable(event.getInventory().getInputEquipment()) && isUpgradePowder(event.getInventory().getInputMineral())) {
                event.setResult(getUpgradeResult(event.getInventory().getInputEquipment(), event.getInventory().getInputMineral()));
            }
        } else if (event.getInventory().getInputEquipment() != null && isUpgradeable(event.getInventory().getInputEquipment())) {
            event.setResult(getPowderResult(event.getInventory().getInputEquipment()));
        }

    }

    @EventHandler
    public void onSmith(SmithItemEvent event) {
        if (!event.getView().equals(view)) return;
        if (event.getInventory().getInputMineral() != null) {
            event.getInventory().getInputMineral().setAmount(event.getInventory().getInputMineral().getAmount() - amountUsed);
        }
        amountUsed = 0;
        view.setCursor(event.getInventory().getResult());
        event.getInventory().setInputEquipment(null);
        event.getInventory().setResult(null);
    }

    private ItemStack getPowderResult(ItemStack inputEquipment) {
        return Upgrades.getPowder(inputEquipment);
    }

    private ItemStack getUpgradeResult(ItemStack inputEquipment, ItemStack inputMineral) {
        ItemStack item = inputEquipment.clone();
        Trait trait = Trait.valueOf(inputMineral.getItemMeta().getPersistentDataContainer().get(Trait.getTraitKey(), PersistentDataType.STRING));
        if (Trait.hasTrait(item, trait)) {
            amountUsed = Trait.increaseTrait(item, trait, inputMineral.getAmount());
            return item;
        } else {
            return null;
        }
    }

    private boolean isUpgradePowder(ItemStack inputMineral) {
        ItemMeta meta = inputMineral.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().get(Trait.getTraitKey(), PersistentDataType.STRING) != null;
    }

    private boolean isUpgradeable(ItemStack inputEquipment) {
        return Upgrades.getUpgrades(inputEquipment) < Upgrades.getMaxUpgrades() &&
                getPowderResult(inputEquipment) != null
                && !getPowderResult(inputEquipment).getType().equals(Material.AIR);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().equals(view)) {
            HandlerList.unregisterAll(this);
        }
    }
}
