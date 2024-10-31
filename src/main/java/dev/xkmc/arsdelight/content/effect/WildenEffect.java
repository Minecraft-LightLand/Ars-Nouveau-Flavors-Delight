package dev.xkmc.arsdelight.content.effect;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class WildenEffect extends MobEffect {

	public WildenEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		var id = ArsDelight.loc("wilden");
		addAttributeModifier(PerkAttributes.MAX_MANA, id, 0.2,
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		addAttributeModifier(PerkAttributes.MANA_REGEN_BONUS, id, 0.2,
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		addAttributeModifier(PerkAttributes.SPELL_DAMAGE_BONUS, id, 0.2,
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

}
