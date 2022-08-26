package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Headless extends CustomEnchant implements Listener {
    private static Headless instance = null;


    public Headless() {
        super("headless", "Headless", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Headless Enchant (report this to developer)");
        }

        setAcceptsAxes(true);
        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    @SuppressWarnings("deprecation")
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damageDealer && event.getEntity() instanceof Player damaged) {

            ItemStack item = damageDealer.getInventory().getItemInMainHand();
            if (get(item) > 0) {
                int chance = 2 * get(item);
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    if (Utils.hasStorageSpace(damageDealer.getInventory())) {
                        ItemStack stolenPiece = damaged.getInventory().getHelmet();
                        if (stolenPiece == null || stolenPiece.getType() == Material.AIR) return;
                        damaged.getInventory().remove(stolenPiece);
                        damageDealer.getInventory().addItem(stolenPiece);
                        damageDealer.sendMessage(ChatColor.GREEN + "Stolen " + Utils.getDisplayName(stolenPiece) + " from " + damaged.getDisplayName());
                        damaged.sendMessage(ChatColor.RED + damageDealer.getDisplayName() + " stole " + Utils.getDisplayName(stolenPiece) + " from you!");

                    }
                }
            }

        }

    }


    public static Headless getInstance() {
        return instance;
    }
}
