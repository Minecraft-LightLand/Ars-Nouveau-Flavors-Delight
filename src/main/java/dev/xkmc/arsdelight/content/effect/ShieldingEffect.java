package dev.xkmc.arsdelight.content.effect;

import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.ADModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShieldingEffect extends MobEffect {

	public ShieldingEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(Attributes.MAX_ABSORPTION, ArsDelight.loc("synchronized_shield"),
				AttributeModifier.Operation.ADD_VALUE, ShieldingEffect::getVal);
	}

	private static double getVal(int lv) {
		double val = ADModConfig.SERVER.getOrDefault(e -> e.maxShieldingAbsorption);
		int factor = 1 << lv;
		return val * factor;
	}

}
