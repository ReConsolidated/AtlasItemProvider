package io.github.reconsolidated.atlasitemprovider.Particles.Styles;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Test {
    public static void run(Player player) {
        World world = player.getWorld();
        Location start = player.getEyeLocation().clone();
        Vector direction = player.getEyeLocation().getDirection();
        for (int i = 0; i<10; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(AtlasItemProvider.plugin, () -> {
                Location current = start.clone().add(direction.clone().multiply(0.5 * finalI));
                current.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, current, 4, 0, 0, 0, 0);
            }, 4 * i);

            new BukkitRunnable() {
                Location loc = player.getLocation();
                double t = 0;
                double r = 1;

                @Override
                public void run() {
                    t = t + Math.PI / 8;
                    double x = r * cos(t);
                    double y = t/4;
                    double z = r * sin(t);
                    world.spawnParticle(Particle.FLAME, loc.clone().add(x, y, z), 1, 0, 0, 0, 0);
                    if (t > Math.PI * 4) {
                        cancel();
                    }
                }
            }.runTaskTimer(AtlasItemProvider.plugin, 0L, 1L);
        }
    }
}
