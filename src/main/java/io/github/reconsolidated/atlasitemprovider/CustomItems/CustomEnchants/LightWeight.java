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

public class LightWeight extends CustomEnchant implements Listener {
    private static LightWeight instance = null;


    public LightWeight() {
        super("light_weight", ChatColor.YELLOW + "" + ChatColor.BOLD + "Lightweight", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of LightWeight Enchant (report this to developer)");
        }

        maxLevel = 4;

        setAcceptsSwords(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            int value = get(item);
            if (value > 0) {
                Random random = new Random();
                if (value * 10 > random.nextInt(100)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40 * value,value));
                }
            }
        }
    }


    public static LightWeight getInstance() {
        return instance;
    }
}

