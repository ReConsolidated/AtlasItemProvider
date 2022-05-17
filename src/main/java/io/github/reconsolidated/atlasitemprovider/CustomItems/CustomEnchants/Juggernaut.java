package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Juggernaut extends CustomEnchant {
    private static Juggernaut instance = null;


    public Juggernaut() {
        super("juggernaut", "Juggernaut", Rarity.RARE);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Juggernaut Enchant (report this to developer)");
        }

        maxLevel = 5;

        setAcceptsArmors(true);
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        ItemMeta meta = item.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(
                UUID.randomUUID(),
                "juggernaut_health",
                0.5 * value,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(
                UUID.randomUUID(),
                "juggernaut_health",
                0.5 * value,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(
                UUID.randomUUID(),
                "juggernaut_health",
                0.5 * value,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(
                UUID.randomUUID(),
                "juggernaut_health",
                0.5 * value,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
    }

    public static Juggernaut getInstance() {
        return instance;
    }
}

