package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Aquatic extends CustomEnchant {
    private static Aquatic instance = null;

    public Aquatic() {
        super("aquatic", "Aquatic", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Aquatic Enchant (report this to developer)");
        }
        maxLevel = 2;

        setAcceptsHelmets(true);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack item = player.getInventory().getHelmet();
                if (item != null && item.getItemMeta() != null) {
                    if (get(item) > 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 100, 1));
                    }
                    if (get(item) > 1) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 1));
                    }
                }
            }
        }, 0L, 10L);

    }



    public static Aquatic getInstance() {
        return instance;
    }
}
