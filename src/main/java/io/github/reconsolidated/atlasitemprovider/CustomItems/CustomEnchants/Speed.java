package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Speed extends CustomEnchant implements Listener {
    private static Speed instance = null;


    public Speed() {
        super("speed", ChatColor.YELLOW + "" + ChatColor.BOLD + "Speed", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Speed Enchant (report this to developer)");
        }

        maxLevel=3;
        setAcceptsBoots(true);


        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getItemMeta() != null) {
                    if (get(item) > 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, get(item)-1));
                    }
                }
            }
        }, 0L, 10L);


    }



    public static Speed getInstance() {
        return instance;
    }
}
