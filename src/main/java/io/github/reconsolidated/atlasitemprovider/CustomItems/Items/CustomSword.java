package io.github.reconsolidated.atlasitemprovider.CustomItems.Items;


import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.CritChance;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Damage;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Durability;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.HuntingLuck;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

// Stats: Damage, Durability, Crit Chance, Hunting Luck
public class CustomSword {
    public static ItemStack createCustomSword(String name, Material material, double damage, int maxDurability, double critChance, double huntingLuck) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();


        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "generic.attackDamage",
                damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

        meta.getPersistentDataContainer().set(Damage.getDamageKey(), PersistentDataType.DOUBLE, damage);
        meta.getPersistentDataContainer().set(Durability.getMaxDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(Durability.getDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(CritChance.getCritChanceKey(), PersistentDataType.DOUBLE, critChance);
        meta.getPersistentDataContainer().set(HuntingLuck.getHuntingLuckKey(), PersistentDataType.DOUBLE, huntingLuck);

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
