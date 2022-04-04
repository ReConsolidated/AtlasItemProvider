package io.github.reconsolidated.atlasitemprovider.CustomItems.Items;

import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.*;
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

public class CustomArmor {
    public static ItemStack createCustomArmor(String name, Material material, double armor, int maxDurability, double magic) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                new AttributeModifier(UUID.randomUUID(), "generic.armor",
                        armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                new AttributeModifier(UUID.randomUUID(), "generic.armor",
                        armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                new AttributeModifier(UUID.randomUUID(), "generic.armor",
                        armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                new AttributeModifier(UUID.randomUUID(), "generic.armor",
                        armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

        meta.getPersistentDataContainer().set(Armor.getArmorKey(), PersistentDataType.DOUBLE, armor);
        meta.getPersistentDataContainer().set(Durability.getMaxDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        meta.getPersistentDataContainer().set(Durability.getDurabilityKey(), PersistentDataType.INTEGER, maxDurability);
        // todo ADD MAGIC


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
