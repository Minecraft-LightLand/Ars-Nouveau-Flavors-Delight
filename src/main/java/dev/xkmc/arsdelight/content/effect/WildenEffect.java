package dev.xkmc.arsdelight.content.effect;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class WildenEffect extends MobEffect {

	public WildenEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		UUID id = MathHelper.getUUIDFromString("arsdelight_wilden_effect");
		addAttributeModifier(PerkAttributes.MANA_REGEN_BONUS.get(), id.toString(),
				ServerConfig.MANA_REGEN_POTION.get(), AttributeModifier.Operation.ADDITION);
		addAttributeModifier(PerkAttributes.MAX_MANA.get(), id.toString(), 0.2,
				AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

}
