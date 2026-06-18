package dev.xkmc.arsdelight.compat.elemental;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class LightningCurseEffect extends MobEffect {

	protected LightningCurseEffect() {
		super(MobEffectCategory.BENEFICIAL, ParticleColor.YELLOW.getColor());
	}

}
