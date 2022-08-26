package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
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

public class LightsOut extends CustomEnchant implements Listener {
    private static LightsOut instance = null;


    public LightsOut() {
        super("lights_out", "Lights Out", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of LightsOut Enchant (report this to developer)");
        }

        setAcceptsSwords(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }


    @EventHandler
    @SuppressWarnings("deprecation")
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damageDealer && event.getEntity() instanceof Player damaged) {

            ItemStack item = damageDealer.getInventory().getItemInMainHand();
            if (get(item) > 0) {
                int chance = 12 * get(item);
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
                    damageDealer.sendMessage(ChatColor.GREEN + "You have blinded " + damaged.getDisplayName());
                }
            }
        }

    }


    public static LightsOut getInstance() {
        return instance;
    }
}

