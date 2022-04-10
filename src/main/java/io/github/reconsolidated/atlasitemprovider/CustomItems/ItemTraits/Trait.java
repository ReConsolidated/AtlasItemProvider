package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith.Upgrades;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public enum Trait {
    ARMOR, BOW_DAMAGE, CRIT_CHANCE, DAMAGE, DURABILITY, HUNTING_LUCK, TOOL_LUCK;

    public static NamespacedKey getTraitKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_trait");
    }

    public static boolean hasTrait(ItemStack item, Trait trait) {
        return getTraitValue(item, trait) != null;
    }

    public static Double getTraitValue(ItemStack item, Trait trait) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || item.getType() == Material.AIR) {
            return null;
        }

        try {
            return switch (trait) {
                case ARMOR -> meta.getPersistentDataContainer().get(Armor.getArmorKey(), PersistentDataType.DOUBLE);
                case BOW_DAMAGE ->  meta.getPersistentDataContainer().get(BowDamage.getBowDamageKey(), PersistentDataType.DOUBLE);
                case CRIT_CHANCE -> meta.getPersistentDataContainer().get(CritChance.getCritChanceKey(), PersistentDataType.DOUBLE);
                case DAMAGE -> meta.getPersistentDataContainer().get(Damage.getDamageKey(), PersistentDataType.DOUBLE);
                case DURABILITY -> (double) meta.getPersistentDataContainer().get(Durability.getDurabilityKey(), PersistentDataType.INTEGER);
                case HUNTING_LUCK -> meta.getPersistentDataContainer().get(HuntingLuck.getHuntingLuckKey(), PersistentDataType.DOUBLE);
                case TOOL_LUCK -> meta.getPersistentDataContainer().get(ToolLuck.getToolLuckKey(), PersistentDataType.DOUBLE);
            };
        } catch (NullPointerException exception) {
            return null;
        }


    }

    /**
     *
     * @param item
     * @param trait
     * @return amount of trait item used
     */
    public static int increaseTrait(ItemStack item, Trait trait, int amount) {
        int used = 0;

        ItemMeta meta = item.getItemMeta();
        if (trait == null || meta == null || item.getType() == Material.AIR) {
            return 0;
        }

        NamespacedKey key;
        switch (trait) {
                case ARMOR -> {
                    key = Armor.getArmorKey();
                }
                case BOW_DAMAGE ->  {
                    key = BowDamage.getBowDamageKey();
                }
                case CRIT_CHANCE -> {
                    key = CritChance.getCritChanceKey();
                }
                case DAMAGE -> {
                    key = Damage.getDamageKey();
                }
                case DURABILITY -> {
                    key = Durability.getDurabilityKey();
                }
                case HUNTING_LUCK -> {
                    key = HuntingLuck.getHuntingLuckKey();
                }
                case TOOL_LUCK -> {
                    key = ToolLuck.getToolLuckKey();
                }
            default -> throw new IllegalStateException("Unexpected value: " + trait);
        }

        if (trait == DURABILITY) {
            Integer currentValue = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
            currentValue += 30 * amount;
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, currentValue);
            used = amount;
        } else {
            Double currentValue = meta.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
            if (currentValue == null) return 0;
            for (int i = 0; i<amount; i++) {
                item.setItemMeta(meta);
                if (Upgrades.getUpgrades(item) < Upgrades.getMaxUpgrades() && canUpgrade(trait, currentValue)) {
                    currentValue = upgrade(trait, currentValue);
                    currentValue = Math.round(currentValue * 100.0)/100.0;
                    meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, currentValue);
                    used++;
                    item.setItemMeta(meta);
                    Upgrades.setUpgrades(item, Upgrades.getUpgrades(item) + 1);
                    meta = item.getItemMeta();
                }
            }
        }


        item.setItemMeta(meta);
        meta.lore(LoreProvider.getLore(item));
        item.setItemMeta(meta);
        return used;
    }

    private static Double upgrade(Trait trait, Double currentValue) {
        double amount = getTraitUpgradeAmount(trait, currentValue);
        return currentValue + amount;
    }

    private static double getTraitUpgradeAmount(Trait trait, Double currentValue) {
        return 2.0;
    }

    private static boolean canUpgrade(Trait trait, Double currentValue) {
        double maxValue = getTraitMaxValue(trait);
        return maxValue >= currentValue + getTraitUpgradeAmount(trait, currentValue);
    }

    private static double getTraitMaxValue(Trait trait) {
        return 100.0;
    }
}