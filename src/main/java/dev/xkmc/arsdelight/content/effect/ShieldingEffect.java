package dev.xkmc.arsdelight.content.effect;

import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.ADModConfig;
import dev.xkmc.l2core.util.Proxy;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

public class ShieldingEffect extends MobEffect {

	public ShieldingEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(Attributes.MAX_ABSORPTION, ArsDelight.loc("synchronized_shield"),
				AttributeModifier.Operation.ADD_VALUE, ShieldingEffect::getVal);
	}

	private static double getVal(int lv) {
		double val = DatagenModLoader.isRunningDataGen() || Proxy.getLevel() == null ?
				ADModConfig.SERVER.maxShieldingAbsorption.getDefault() :
				ADModConfig.SERVER.maxShieldingAbsorption.get();
		int factor = 1 << lv;
		return val * factor;
	}

}
