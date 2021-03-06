package io.github.reconsolidated.atlasitemprovider.Particles;

import dev.esophose.playerparticles.particles.FixedParticleEffect;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.ParticlePair;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.ParticleStyle;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.ppAPI;

public class TempPPEffect {

    public static void createFixed(ParticleEffect pEffect, ParticleStyle style, Location location, long ticks) {
        FixedParticleEffect effect = ppAPI.createFixedParticleEffect(
                Bukkit.getConsoleSender(),
                location,
                pEffect, style);

        Bukkit.getScheduler().runTaskLater(AtlasItemProvider.plugin, () -> {
            if (effect != null) {
                ppAPI.removeFixedEffect(Bukkit.getConsoleSender(), effect.getId());
            }
        }, ticks);
    }

    public static void createPlayer(ParticleEffect pEffect, ParticleStyle style, Player player, long ticks) {
        ParticlePair effect = ppAPI.addActivePlayerParticle(
                player,
                pEffect, style);

        Bukkit.getScheduler().runTaskLater(AtlasItemProvider.plugin, () -> {
            if (player != null && effect != null) {
                ppAPI.removeActivePlayerParticle(player, effect.getId());
            }
        }, ticks);
    }

    public static void createPlayer(ParticleEffect pEffect, ParticleStyle style, Player player, long ticks, OrdinaryColor color) {
        ParticlePair effect = ppAPI.addActivePlayerParticle(
                player,
                pEffect, style, color);

        Bukkit.getScheduler().runTaskLater(AtlasItemProvider.plugin, () -> {
            if (player != null && effect != null) {
                ppAPI.removeActivePlayerParticle(player, effect.getId());
            }        }, ticks);
    }
}
