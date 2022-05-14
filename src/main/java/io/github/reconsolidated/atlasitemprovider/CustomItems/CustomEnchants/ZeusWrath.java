package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Particles.TempPPEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ZeusWrath extends CustomEnchant implements Listener {
    private static ZeusWrath instance = null;


    public ZeusWrath() {
        super("zeus_wrath", ChatColor.YELLOW + "" + ChatColor.BOLD + "Zeus's Wrath", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of ZeusWrath Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();

        ItemStack item = attacker.getInventory().getItem(attacker.getInventory().getHeldItemSlot());
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        Integer chaosLevel = get(item);
        if (chaosLevel == null || chaosLevel == 0) {
            return;
        }

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) event.getEntity();
            le.getWorld().strikeLightningEffect(le.getLocation());
            le.getWorld().strikeLightningEffect(le.getLocation());
            le.getWorld().strikeLightningEffect(le.getLocation());

            le.damage(2 * chaosLevel);

            if (le instanceof Player) {
                TempPPEffect.createPlayer(ParticleEffect.DUST, DefaultStyles.NORMAL,
                        (Player) le, 60L, new OrdinaryColor(255,255,0));

            }

        }
    }


    public static ZeusWrath getInstance() {
        return instance;
    }
}
