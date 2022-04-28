package io.github.reconsolidated.atlasitemprovider.CustomItems.Items;

import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith.Upgrades;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.BowDamage;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.CritChance;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Durability;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.HuntingLuck;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


// Stats: Damage, Durability, Crit Chance, Hunting Luck
public class CustomBow {
    public static ItemStack createCustomBow(String name, Material material, double damage, int maxDurability, double critChance, double huntingLuck) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(Upgrades.getUpgradesKey(), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(BowDamage.getInstance().getKey(), PersistentDataType.DOUBLE, damage);
        meta.getPersistentDataContainer().set(Durability.getMaxDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(Durability.getDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(CritChance.getInstance().getKey(), PersistentDataType.DOUBLE, critChance);
        meta.getPersistentDataContainer().set(HuntingLuck.getInstance().getKey(), PersistentDataType.DOUBLE, huntingLuck);

        meta.displayName(
                Component.text(ColorHelper.translate(name))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true)
        );

        item.setItemMeta(meta);

        meta.lore(LoreProvider.getLore(item));

        item.setItemMeta(meta);
        return item;
    }

}
