package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import io.github.reconsolidated.atlasitemprovider.Particles.TempPPEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PoseidonCurse extends CustomEnchant implements Listener {
    private static PoseidonCurse instance = null;
    private final int BASE_CHANCE = 10;


    public PoseidonCurse() {
        super("poseidon_curse", ChatColor.YELLOW + "" + ChatColor.BOLD + "Poseidon's Curse", Rarity.LEGENDARY);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of PoseidonCurse Enchant (report this to developer)");
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Trident) {
            Trident trident = (Trident) event.getDamager();
            ItemStack item = trident.getItemStack();
            int enchant = get(item);
            if (enchant > 0) {
                int chance = BASE_CHANCE * enchant;
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    if (event.getEntity() instanceof Player) {
                        Player player = (Player) event.getEntity();
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 8 * 20, 1));

                        TempPPEffect.createPlayer(ParticleEffect.DUST, DefaultStyles.NORMAL,
                                player, 60L, new OrdinaryColor(40,200,255));
                    }
                }
            }
        }
    }

    public static PoseidonCurse getInstance() {
        return instance;
    }
}
