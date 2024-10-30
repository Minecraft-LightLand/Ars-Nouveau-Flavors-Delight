package dev.xkmc.arsdelight.init.food;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Supplier;

public record EffectEntry(Supplier<MobEffect> eff,
						  int duration,
						  int amplifier,
						  float chance) {

	public EffectEntry(Supplier<MobEffect> eff,
					   int duration,
					   int amplifier) {
		this(eff, duration, amplifier, 1);
	}

	public EffectEntry(Supplier<MobEffect> eff,
					   int duration) {
		this(eff, duration, 0, 1);
	}

	public MobEffectInstance getEffect() {
		return new MobEffectInstance(eff.get(), duration, amplifier);
	}
}
