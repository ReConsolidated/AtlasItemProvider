package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Evacuate extends CustomEnchant implements Listener {
    private static Evacuate instance = null;


    public Evacuate() {
        super("evacuate", ChatColor.YELLOW + "" + ChatColor.BOLD + "Evacuate");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Evacuate Enchant (report this to developer)");
        }

        setAcceptsArmors(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (player.getHealth() - event.getFinalDamage() < player.getHealth() * 0.3) {
                int level = 0;
                for (ItemStack item : player.getInventory().getArmorContents()) {
                    level = Math.max(get(item), level);
                }
                if (level > 0) {
                    int chance = 5;
                    Random random = new Random();
                    if (chance > random.nextInt(100)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, level-1));
                        player.sendMessage(ChatColor.GREEN + "Evacuation!");
                    }
                }
            }
        }
    }

    public static Evacuate getInstance() {
        return instance;
    }
}

