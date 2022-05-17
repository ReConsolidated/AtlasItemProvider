package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class CronusCropduster extends CustomEnchant implements Listener {
    private static CronusCropduster instance = null;
    private final int BASE_CHANCE = 10;


    public CronusCropduster() {
        super("cronus_cropduster", "Cronus Cropduster", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of CronusCropduster Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Trident) {
            Trident trident = (Trident) event.getDamager();
            ItemStack item = trident.getItemStack();
            int enchant = get(item);
            if (enchant > 0) {
                int chance = BASE_CHANCE * enchant;
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    if (event.getEntity() instanceof Player) {
                        Player player = (Player) event.getEntity();
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 8, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 8, 1));
                    }
                }
            }
        }
    }

    public static CronusCropduster getInstance() {
        return instance;
    }
}
