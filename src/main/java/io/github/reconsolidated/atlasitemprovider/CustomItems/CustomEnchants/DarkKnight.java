package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DarkKnight extends CustomEnchant implements Listener {
    private static DarkKnight instance = null;
    private final Map<UUID, ItemStack> projectileBows = new HashMap<>();

    public DarkKnight() {
        super("dark_knight", ChatColor.YELLOW + "" + ChatColor.BOLD + "Dark Knight", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of DarkKnight Enchant (report this to developer)");
        }

        maxLevel = 4;

        setAcceptsSwords(true);
        setAcceptsAxes(true);
        setAcceptsBows(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (projectileBows.size() > 1000) {
            projectileBows.clear();
        }

        if (event.getDamager() instanceof Projectile) {
            Projectile proj = (Projectile) event.getDamager();
            if (proj.getShooter() instanceof Player) {
                ItemStack bow = projectileBows.get(proj.getUniqueId());
                if (event.getEntity() instanceof Player) {
                    apply(bow, event);
                }
            }
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            apply(item, event);
        }
    }

    private void apply(ItemStack item, EntityDamageByEntityEvent event) {
        int level = get(item);
        if (level > 0) {
            double increase = 1 + level * 0.2;
            event.setDamage(event.getDamage() * increase);
        }
    }

    @EventHandler
    public void bowShootEvent(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && event.getBow() != null) {
            int level = get(event.getBow());
            if (level > 0) {
                projectileBows.put(event.getProjectile().getUniqueId(), event.getBow());
            }
        }
    }


    public static DarkKnight getInstance() {
        return instance;
    }
}