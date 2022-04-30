package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class StrongWilled  extends CustomEnchant implements Listener {
    private static StrongWilled instance = null;


    public StrongWilled() {
        super("strong_willed", ChatColor.YELLOW + "" + ChatColor.BOLD + "Strong Willed");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of StrongWilled Enchant (report this to developer)");
        }

        setAcceptsSwords(true);
        setAcceptsAxes(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.getItemMeta() != null) {
                int level = get(item);
                if (level > 0) {
                    Random random = new Random();
                    int chance = 20;
                    if (chance > random.nextInt(100)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, level));
                    }
                }
            }
        }
    }


    public static StrongWilled getInstance() {
        return instance;
    }
}
