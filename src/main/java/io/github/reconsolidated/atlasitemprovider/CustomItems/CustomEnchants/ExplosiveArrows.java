package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ExplosiveArrows extends CustomEnchant implements Listener {
    private static ExplosiveArrows instance = null;

    private final Map<UUID, ItemStack> arrowBows;


    public ExplosiveArrows() {
        super("explosive_arrows", ChatColor.YELLOW + "" + ChatColor.BOLD + "Explosive Arrows", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of ExplosiveArrows Enchant (report this to developer)");
        }

        maxLevel = 3;

        setAcceptsBows(true);

        arrowBows = new HashMap<>();
        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() == null) return;
        if (arrowBows.size() > 1000) {
            arrowBows.clear();
            return;
        }
        ItemStack bow = arrowBows.get(event.getEntity().getUniqueId());
        if (bow == null) return;
        int level = get(bow);
        if (level > 0) {
            int chance = 10 * level;
            Random random = new Random();
            if (chance > random.nextInt(100)) {
                if (event.getHitBlock() != null) {
                    event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), 2);
                }
                else if (event.getHitEntity() != null) {
                    event.getHitEntity().getWorld().createExplosion(event.getHitEntity().getLocation(), 2);
                }
            }
        }
    }

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && event.getBow() != null) {
            arrowBows.put(event.getProjectile().getUniqueId(), event.getBow());
        }
    }

    public static ExplosiveArrows getInstance() {
        return instance;
    }
}

