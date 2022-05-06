package io.github.reconsolidated.atlasitemprovider.Particles.Styles;

import org.bukkit.Location;
import org.bukkit.Particle;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Spiral extends ParticleEffect {
    double t = 0;
    double r = 1;

    public Spiral(Location start) {
        super(start);
    }

    @Override
    protected void onTick() {
        t = t + Math.PI / 8;
        double x = r * cos(t);
        double y = t/4;
        double z = r * sin(t);
        world.spawnParticle(Particle.FLAME, startLocation.clone().add(x, y, z), 1, 0, 0, 0, 0);
        if (t > Math.PI * 4) {
            cancel();
        }
    }
}
