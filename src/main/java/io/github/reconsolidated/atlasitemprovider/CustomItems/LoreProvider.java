package io.github.reconsolidated.atlasitemprovider.CustomItems;

import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith.Upgrades;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.*;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Rarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoreProvider {
    public static List<Component> getLore(ItemStack item) {
        List<Component> result = new ArrayList<>();
        result.add(Component.text(""));

        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        Double damage = container.get(Damage.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (damage != null) {
            result.add(Component.text(ColorHelper.translate("&5&lDamage: " +ChatColor.WHITE + damage)));
        }

        Double bowDamage = container.get(BowDamage.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (bowDamage != null) {
            result.add(Component.text(ColorHelper.translate("&5&lBow Damage: " +ChatColor.WHITE + bowDamage)));
        }

        Double toolLuck = container.get(ToolLuck.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (toolLuck != null) {
            result.add(Component.text(ColorHelper.translate(getToolLuckText(item) + ChatColor.WHITE + toolLuck)));
        }

        Double armor = container.get(Armor.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (armor != null) {
            result.add(Component.text(ColorHelper.translate("&5&lArmor: " +ChatColor.WHITE + armor)));
        }

        Double critChance = container.get(CritChance.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (critChance != null) {
            result.add(Component.text(ColorHelper.translate("&5&lCrit Chance: " + ChatColor.WHITE + critChance)));
        }

        Double huntLuck = container.get(HuntingLuck.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (huntLuck != null) {
            result.add(Component.text(ColorHelper.translate("&5&lHunting Luck: " +ChatColor.WHITE + huntLuck)));
        }

        Integer durability = container.get(Durability.getDurabilityKey(), PersistentDataType.INTEGER);
        if (durability != null) {
            result.add(Component.text(ColorHelper.translate("&5&lDurability: " + ChatColor.WHITE + durability)));
        }

        Integer upgrades = container.get(Upgrades.getUpgradesKey(), PersistentDataType.INTEGER);
        if (upgrades != null) {
            result.add(Component.text(ColorHelper.translate("&5&lUpgrades: " + ChatColor.WHITE + upgrades + "/" + Upgrades.getMaxUpgrades())));
        }

        Double rarity = Rarity.getInstance().get(item);
        if (rarity != null) {
            result.add(Component.text(ColorHelper.translate("&5&lRarity: " + Rarity.getInstance().getRarity(item).getDisplayName())));
        }

        if (item.getType() != Material.ENCHANTED_BOOK) {
            int rank = (int) (double) Rank.getInstance().get(item);
            result.add(Component.text(ColorHelper.translate("&5&lRank: " + ChatColor.WHITE + rank)));
        }




        if (result.size() > 1) {
            result.add(Component.text(""));
        }


        List<CustomEnchant> enchants = CustomEnchant.getEnchants(item).keySet().stream()
                .sorted(Comparator.comparing(CustomEnchant::getRarity).reversed()).collect(Collectors.toList());
        for (CustomEnchant enchant : enchants) {
            result.add(Component.text(ColorHelper.translate(enchant.getDisplayName() + ": " + ChatColor.WHITE + enchant.get(item))));
        }


        for (int i = 0; i<result.size(); i++) {
            result.set(i, result.get(i).decoration(TextDecoration.ITALIC, false));
        }

        if (result.size() > 2) {
            result.add(Component.text(""));
        }

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
            return "&5&lMining Luck: ";
        }
        if (item.getType().toString().contains("AXE")) {
            return "&5&lChopping Luck: ";
        }
        if (item.getType().toString().contains("SHOVEL")) {
            return "&5&lShoveling Luck: ";
        }
        return "&5&lLuck: ";
    }
}
