package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Particles.TempPPEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class HadeSoulTrade extends CustomEnchant implements Listener {
    private static HadeSoulTrade instance = null;

    public HadeSoulTrade() {
        super("hade_soul_trade", "Hade's Soul Trade", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of HadeSoulTrade Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @Override
    public void set(ItemStack item, int value) {
        super.set(item, value);
        ItemMeta meta = item.getItemMeta();
        double decreasedHealth = -1 * value * 3;
        double increaseDamage = 2 * value;
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,
                new AttributeModifier(UUID.randomUUID(), "hade_soul_trade_health", decreasedHealth,
                        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),
                "hade_soul_trade_damage", increaseDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        item.setItemMeta(meta);
    }

    @EventHandler
    public void onItemChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        if (item == null || item.getItemMeta() == null) return;
        int level = get(item);
        if (level > 0) {
            TempPPEffect.createPlayer(ParticleEffect.DUST, DefaultStyles.PULSE,
                    player, 60L);
        }
    }

    public static HadeSoulTrade getInstance() {
        return instance;
    }
}
