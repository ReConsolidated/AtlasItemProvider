package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class GlowingAura extends CustomEnchant {
    private static GlowingAura instance = null;


    public GlowingAura() {
        super("glowing_aura", "Glowing Aura", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of GlowingAura Enchant (report this to developer)");
        }

        setAcceptsHelmets(true);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack item = player.getInventory().getHelmet();
                if (item != null && item.getItemMeta() != null) {
                    if (get(item) > 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 1));
                    }
                }
            }
        }, 0L, 10L);
    }



    public static GlowingAura getInstance() {
        return instance;
    }
}
