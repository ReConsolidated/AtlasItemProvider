package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class AchillesHeel extends CustomEnchant implements Listener {
    private static AchillesHeel instance = null;
    private final int BASE_CHANCE = 10;


    public AchillesHeel() {
        super("achilles_heel", ChatColor.YELLOW + "" + ChatColor.BOLD + "Achilles Heel");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of AchillesHeel Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        ItemMeta meta = item.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(),
                "achilles_heel", value, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        item.setItemMeta(meta);
    }

    @EventHandler
    public void onItemDamaged(PlayerItemDamageEvent event) {
        Integer level = get(event.getItem());
        if (level > 0) {
            event.setDamage(event.getDamage() * level);
        }
    }

    public static AchillesHeel getInstance() {
        return instance;
    }
}