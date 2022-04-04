package io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith;

import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.*;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Upgrades {
    public static ItemStack getPowder(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer container = meta.getPersistentDataContainer();

        List<Pair<Trait, Double>> traitValues = new ArrayList<>();

        Double damage = container.get(Damage.getDamageKey(), PersistentDataType.DOUBLE);
        if (damage != null) {
            traitValues.add(Pair.of(Trait.DAMAGE, damage));
        }

        Double bowDamage = container.get(BowDamage.getBowDamageKey(), PersistentDataType.DOUBLE);
        if (bowDamage != null) {
            traitValues.add(Pair.of(Trait.BOW_DAMAGE, bowDamage));
        }

        Double toolLuck = container.get(ToolLuck.getToolLuckKey(), PersistentDataType.DOUBLE);
        if (toolLuck != null) {
            traitValues.add(Pair.of(Trait.TOOL_LUCK, toolLuck));
        }

        Double armor = container.get(Armor.getArmorKey(), PersistentDataType.DOUBLE);
        if (armor != null) {
            traitValues.add(Pair.of(Trait.ARMOR, armor));
        }

        Double critChance = container.get(CritChance.getCritChanceKey(), PersistentDataType.DOUBLE);
        if (critChance != null) {
            traitValues.add(Pair.of(Trait.CRIT_CHANCE, critChance));
        }

        Double huntLuck = container.get(HuntingLuck.getHuntingLuckKey(), PersistentDataType.DOUBLE);
        if (huntLuck != null) {
            traitValues.add(Pair.of(Trait.HUNTING_LUCK, huntLuck));
        }

        Integer durability = container.get(Durability.getDurabilityKey(), PersistentDataType.INTEGER);
        if (durability != null) {
            traitValues.add(Pair.of(Trait.DURABILITY, (double) durability));
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
}
