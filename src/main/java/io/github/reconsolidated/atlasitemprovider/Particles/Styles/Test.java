package io.github.reconsolidated.atlasitemprovider.Particles.Styles;

import dev.esophose.playerparticles.particles.FixedParticleEffect;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.ppAPI;

public class Test {
    public static void run(Player player) {
        FixedParticleEffect effect = ppAPI.createFixedParticleEffect(
                Bukkit.getConsoleSender(),
                player.getLocation().clone().add(0, 5, 0),
                ParticleEffect.TOTEM_OF_UNDYING, ParticleStyle.fromName("halo"));

        Bukkit.getScheduler().runTaskLater(AtlasItemProvider.plugin, () -> {
            ppAPI.removeFixedEffect(Bukkit.getConsoleSender(), effect.getId());
        }, 100L);
    }
}
