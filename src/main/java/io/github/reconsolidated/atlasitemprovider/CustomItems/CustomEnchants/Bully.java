package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Bully extends CustomEnchant implements Listener {
    private static Bully instance = null;


    public Bully() {
        super("bully", "Bully", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Bully Enchant (report this to developer)");
        }

        setAcceptsAxes(true);
        setAcceptsGloves(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            ItemStack item = damager.getInventory().getItemInMainHand();
            if (get(item) > 0) {
                int chance = 2 * get(item);
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    if (Utils.hasStorageSpace(damager.getInventory())) {
                        int armorPiece = random.nextInt(4);
                        ItemStack stolenPiece = damaged.getInventory().getArmorContents()[armorPiece];
                        if (stolenPiece == null || stolenPiece.getType() == Material.AIR) return;
                        damaged.getInventory().remove(stolenPiece);
                        damager.getInventory().addItem(stolenPiece);
                        damager.sendMessage(ChatColor.GREEN + "Stolen " + Utils.getDisplayName(stolenPiece) + " from " + damaged.getDisplayName());
                        damaged.sendMessage(ChatColor.RED + damager.getDisplayName() + " stole " + Utils.getDisplayName(stolenPiece) + " from you!");

                    }
                }
            }

        }

    }


    public static Bully getInstance() {
        return instance;
    }
}