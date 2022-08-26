package io.github.reconsolidated.atlasitemprovider.MiningEntities.OilDrill;

import io.github.reconsolidated.atlasitemprovider.CustomInventory.ClickOnlyItem;
import io.github.reconsolidated.atlasitemprovider.CustomInventory.InventoryMenu;
import io.github.reconsolidated.atlasitemprovider.Translations;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class OilDrillInventory extends InventoryMenu {
    private final OilDrill drill;

    public OilDrillInventory(Plugin plugin, Player player, OilDrill drill) {
        super(plugin, player, "Oil Drill", 3);
        this.drill = drill;

        addItem(new ClickOnlyItem(getCurrentLevelItem(), 2, 4, (event) -> {
        }));
        addItem(new ClickOnlyItem(getUpgradeItem(), 2, 6, this::onUpgrade));
    }

    private void onUpgrade(InventoryClickEvent event) {
        if (drill.canUpgrade(player)) {
            drill.upgrade(player);
            player.closeInventory();
            player.sendMessage(Translations.ON_DRILL_UPGRADED);
        }
    }

    private ItemStack getUpgradeItem() {
        ItemStack item = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Upgrade"));
        if (drill.canUpgrade(player)) {
            meta.lore(List.of(
                    Component.text(ChatColor.GRAY + "Upgrade the drill to level " + ChatColor.WHITE + (drill.getLevel() + 1)),
                    Component.text(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + drill.getUpgradeCost() + " coins"),
                    Component.text(ChatColor.GRAY + "Click to upgrade!")
            ));
        } else {
            meta.lore(List.of(
                    Component.text(ChatColor.GRAY + "You already have maximum drill level.")
            ));
        }
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getCurrentLevelItem() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Oil Drill Info"));
        meta.lore(List.of(
                Component.text(ChatColor.GRAY + "Level: " + ChatColor.WHITE + drill.getLevel())
                ));
        item.setItemMeta(meta);
        return item;
    }


}
