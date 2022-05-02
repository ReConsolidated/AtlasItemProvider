package io.github.reconsolidated.atlasitemprovider.CustomItems.Items;

import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith.Upgrades;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Durability;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.ToolLuck;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomTool {
    public static ItemStack createCustomTool(String name, Material material, int maxDurability, double luck) {
        ItemStack item = new ItemStack(material);

        Upgrades.setUpgrades(item, 0);
        Durability.getInstance().set(item, maxDurability);
        ToolLuck.getInstance().set(item, luck);


        ItemMeta meta = item.getItemMeta();


        meta.getPersistentDataContainer().set(Durability.getDurabilityKey(), PersistentDataType.INTEGER, maxDurability);

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
