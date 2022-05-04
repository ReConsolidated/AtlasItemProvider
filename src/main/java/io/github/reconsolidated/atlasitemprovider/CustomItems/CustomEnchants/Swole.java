package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Swole extends CustomEnchant implements Listener {
    private static Swole instance = null;
    private final int baseDamage = 1;

    public Swole() {
        super("swole", ChatColor.YELLOW + "" + ChatColor.BOLD + "Swole", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Swole Enchant (report this to developer)");
        }

        setAcceptsGloves(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity le = (LivingEntity) event.getEntity();
            ItemStack item = player.getInventory().getItemInMainHand();
            int level = get(item);
            if (level > 0) {
                le.damage(baseDamage * level, player);
            }
        }

    }


    public static Swole getInstance() {
        return instance;
    }
}