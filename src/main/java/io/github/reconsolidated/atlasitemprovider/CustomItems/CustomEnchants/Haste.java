package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Haste extends CustomEnchant {
    private static Haste instance = null;


    public Haste() {
        super("haste", ChatColor.YELLOW + "" + ChatColor.BOLD + "Haste");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Haste Enchant (report this to developer)");
        }


        setAcceptsTools(true);


        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getItemMeta() != null) {
                    if (get(item) > 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, get(item)-1));
                    }
                }
            }
        }, 0L, 10L);

    }



    public static Haste getInstance() {
        return instance;
    }
}
