package io.github.reconsolidated.atlasitemprovider.Particles.Styles;

import org.bukkit.Location;
import org.bukkit.Particle;

public class Firework extends ParticleEffect {

    private Location currentLocation;
    private double height = 0;
    private double splashHeight = 4;
    private double step = 0.2;

    public Firework(Location start) {
        super(start);
    }

    @Override
    protected void onTick() {
        if (height < splashHeight) {
            height += step;
            world.spawnParticle(Particle.FLAME, startLocation.clone().add(0, height, 0), 1, 0, 0, 0, 0);
        } else {
            
        }
    }
}
