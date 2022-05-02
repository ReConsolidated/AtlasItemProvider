package io.github.reconsolidated.atlasitemprovider.CustomItems;

import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith.Upgrades;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class LoreProvider {
    public static List<Component> getLore(ItemStack item) {
        List<Component> result = new ArrayList<>();
        result.add(Component.text(""));

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();




        Double damage = container.get(Damage.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (damage != null) {
            result.add(Component.text(ColorHelper.translate("Damage: " + damage)));
        }

        Double bowDamage = container.get(BowDamage.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (bowDamage != null) {
            result.add(Component.text(ColorHelper.translate("Bow Damage: " + bowDamage)));
        }

        Double toolLuck = container.get(ToolLuck.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (toolLuck != null) {
            result.add(Component.text(ColorHelper.translate(getToolLuckText(item) + toolLuck)));
        }

        Double armor = container.get(Armor.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (armor != null) {
            result.add(Component.text(ColorHelper.translate("Armor: " + armor)));
        }

        Double critChance = container.get(CritChance.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (critChance != null) {
            result.add(Component.text(ColorHelper.translate("Crit Chance: " + critChance)));
        }

        Double huntLuck = container.get(HuntingLuck.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (huntLuck != null) {
            result.add(Component.text(ColorHelper.translate("Hunting Luck: " + huntLuck)));
        }

        Integer durability = container.get(Durability.getDurabilityKey(), PersistentDataType.INTEGER);
        if (durability != null) {
            result.add(Component.text(ColorHelper.translate("Durability: " + durability)));
        }

        Integer upgrades = container.get(Upgrades.getUpgradesKey(), PersistentDataType.INTEGER);
        if (upgrades != null) {
            result.add(Component.text(ColorHelper.translate("Upgrades: " + upgrades + "/" + Upgrades.getMaxUpgrades())));
        }

        result.add(Component.text(""));
        int rank = (int) (double) Rank.getInstance().get(item);
        result.add(Component.text(ColorHelper.translate("Rank: " + rank)));


        result.add(Component.text(""));

        for (CustomEnchant enchant : CustomEnchant.getEnchants(item).keySet()) {
            result.add(Component.text(ColorHelper.translate(enchant.getDisplayName() + ": " + ChatColor.WHITE + enchant.get(item))));
        }

        for (int i = 0; i<result.size(); i++) {
            result.set(i, result.get(i).decoration(TextDecoration.ITALIC, false));
        }

        result.add(Component.text(""));
        return result;
    }

    public static void updateLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.lore(getLore(item));
        item.setItemMeta(meta);
        return;
    }

    private static String getToolLuckText(ItemStack item) {
        if (item.getType().toString().contains("PICKAXE")) {
            return "Mining Luck: ";
        }
        if (item.getType().toString().contains("AXE")) {
            return "Chopping Luck: ";
        }
        if (item.getType().toString().contains("SHOVEL")) {
            return "Shoveling Luck: ";
        }
        return "Luck: ";
    }
}
