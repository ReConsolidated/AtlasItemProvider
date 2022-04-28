package io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.*;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Upgrades {
    public static int getMaxUpgrades() {
        return 100;
    }

    public static NamespacedKey getUpgradesKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_upgrades");
    }


    public static ItemStack getPowder(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer container = meta.getPersistentDataContainer();

        List<Pair<Trait, Double>> traitValues = new ArrayList<>();

        Double damage = container.get(Damage.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (damage != null) {
            traitValues.add(Pair.of(Trait.DAMAGE, damage));
        }

        Double bowDamage = container.get(BowDamage.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (bowDamage != null) {
            traitValues.add(Pair.of(Trait.BOW_DAMAGE, bowDamage));
        }

        Double toolLuck = container.get(ToolLuck.getInstance().getKey(), PersistentDataType.DOUBLE);
        if (toolLuck != null) {
            traitValues.add(Pair.of(Trait.TOOL_LUCK, toolLuck));
        }

        Double armor = Armor.getInstance().get(item);
        if (armor != null) {
            traitValues.add(Pair.of(Trait.ARMOR, armor));
        }

        Double critChance = CritChance.getInstance().get(item);
        if (critChance != null) {
            traitValues.add(Pair.of(Trait.CRIT_CHANCE, critChance));
        }

        Double huntLuck = HuntingLuck.getInstance().get(item);
        if (huntLuck != null) {
            traitValues.add(Pair.of(Trait.HUNTING_LUCK, huntLuck));
        }

        Double durability = Durability.getInstance().get(item);
        if (durability != null) {
            traitValues.add(Pair.of(Trait.DURABILITY, durability));
        }


        if (traitValues.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            return getTraitPowder(traitValues.get(random.nextInt(traitValues.size())).first());
        }

    }

    private static ItemStack getTraitPowder(Trait trait) {
        ItemStack item = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Trait.getTraitKey(), PersistentDataType.STRING, trait.name());
        item.setItemMeta(meta);

        meta.displayName(Component.text(ColorHelper.translate(getDustName(trait))).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));

        meta.lore(List.of(
                Component.text(ColorHelper.translate("")),
                Component.text(ColorHelper.translate("Use this Dust to upgrade ")).decoration(TextDecoration.ITALIC, false),
                Component.text( getTraitName(trait) + " stat of an item.").decoration(TextDecoration.ITALIC, false)
                )
        );
        item.setItemMeta(meta);
        return item;
    }

    private static String getTraitName(Trait trait) {
        return switch (trait) {
            case ARMOR -> "Armor";
            case BOW_DAMAGE -> "Bow Damage";
            case CRIT_CHANCE -> "Crit Chance";
            case DAMAGE -> "Damage";
            case DURABILITY -> "Durability";
            case HUNTING_LUCK -> "Hunting Luck";
            case TOOL_LUCK -> "Tool Luck";
        };
    }

    private static String getDustName(Trait trait) {
        return getTraitName(trait) + " Dust";
    }

    public static int getUpgrades(ItemStack inputEquipment) {
        if (inputEquipment.getItemMeta() == null) return 0;
        Integer upgrades = inputEquipment.getItemMeta().getPersistentDataContainer().get(getUpgradesKey(), PersistentDataType.INTEGER);
        if (upgrades == null) return 0;
        return upgrades;
    }

    public static void setUpgrades(ItemStack item, int newUpgrades) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.getPersistentDataContainer().set(getUpgradesKey(), PersistentDataType.INTEGER, newUpgrades);
        item.setItemMeta(meta);
    }
}
