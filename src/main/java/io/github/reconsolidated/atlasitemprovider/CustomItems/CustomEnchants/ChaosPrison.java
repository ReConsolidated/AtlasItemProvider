package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChaosPrison extends CustomEnchant implements Listener {
    private static ChaosPrison instance = null;

    public ChaosPrison() {
        super("chaos_prison", ChatColor.YELLOW + "" + ChatColor.BOLD + "Chaos's Prison");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of ChaosPrison Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player attacker = event.getPlayer();
        if (event.getRightClicked() instanceof Player) {
            Player attacked = (Player) event.getRightClicked();

            ItemStack item = attacker.getInventory().getItem(event.getHand());
            if (item == null) return;
            ItemMeta meta = item.getItemMeta();
            if (meta == null) return;

            Integer chaosLevel = get(item);
            if (chaosLevel == null || chaosLevel == 0) {
                return;
            }
            int duration = 20 * chaosLevel;
            attacked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 100));
            attacked.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 100));
            attacked.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, 100));
            attacked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, 100));
        }

    }


    public static ChaosPrison getInstance() {
        return instance;
    }
}
