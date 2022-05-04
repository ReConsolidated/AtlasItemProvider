package io.github.reconsolidated.atlasitemprovider.Particles.Styles;

import java.util.ArrayList;
import java.util.List;

public abstract class ParticleEffect {
    private static List<ParticleEffect> effects = new ArrayList<>();

    public static void tickParticles() {
        for (ParticleEffect effect : effects) {
            effect.onTick();
        }
    }


    public ParticleEffect() {
        effects.add(this);
    }



    protected abstract void onTick();

    protected void cancel() {
        effects.remove(this);
    }
}
