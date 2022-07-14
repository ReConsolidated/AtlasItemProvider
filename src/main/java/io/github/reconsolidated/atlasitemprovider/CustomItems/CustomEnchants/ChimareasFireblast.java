package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Particles.TempPPEffect;
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

public class ChimareasFireblast extends CustomEnchant implements Listener {
    private static ChimareasFireblast instance = null;
    private final Map<UUID, ItemStack> projectileBows = new HashMap<>();

    public ChimareasFireblast() {
        super("chimareas_fireblast", "Chimareas Fireblast", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of ChimareasFireblast Enchant (report this to developer)");
        }
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
                    apply(bow, (Player) event.getEntity());
                }
            }
        }
    }

    private void apply(ItemStack item, Player target) {
        int level = get(item);
        if (level > 0) {
            target.setFireTicks(5 * 20);
            target.damage(10);
            TempPPEffect.createPlayer(ParticleEffect.FLAME, DefaultStyles.SPIRAL,
                    target, 5 * 20);
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


    public static ChimareasFireblast getInstance() {
        return instance;
    }
}
