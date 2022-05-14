package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Cooldown;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Particles.TempPPEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MedusaMadness extends CustomEnchant implements Listener {
    private static MedusaMadness instance = null;
    private final Map<UUID, ItemStack> projectileBows = new HashMap<>();


    public MedusaMadness() {
        super("medusa_madness", ChatColor.YELLOW + "" + ChatColor.BOLD + "Medusa's Madness", Rarity.EPIC);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of MedusaMadness Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (projectileBows.size() > 1000) {
            projectileBows.clear();
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (event.getEntity() instanceof Player) {
                apply(item, (Player) event.getEntity());
            }
        }
        else if (event.getDamager() instanceof Projectile) {
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
        if (!Cooldown.getInstance().isOnCooldown(item)) {
            int level = get(item);
            if (level > 0) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 15 * 20, level));
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15 * 20, level));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15 * 20, level));

                TempPPEffect.createPlayer(ParticleEffect.DUST, DefaultStyles.NORMAL,
                        target, 15 * 20, new OrdinaryColor(35, 82, 37));
                TempPPEffect.createPlayer(ParticleEffect.DUST, DefaultStyles.NORMAL,
                        target, 15 * 20, new OrdinaryColor(142, 148, 142));

                Cooldown.getInstance().set(item, 60000L);
            }
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


    public static MedusaMadness getInstance() {
        return instance;
    }
}
