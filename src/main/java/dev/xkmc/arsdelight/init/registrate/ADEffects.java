package dev.xkmc.arsdelight.init.registrate;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.arsdelight.content.effect.*;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ADEffects {

	public static final SimpleEntry<MobEffect> BLAST_RES = genEffect("blast_resistance",
			() -> new BlastResistanceEffect(MobEffectCategory.BENEFICIAL, 0xffd75525),
			"Reduce incoming explosion damage");

	public static final SimpleEntry<MobEffect> FLOURISH = genEffect("flourishing",
			() -> new FlourishingEffect(MobEffectCategory.BENEFICIAL, 0xff860639),
			"When player heals, recover mana as well");

	public static final SimpleEntry<MobEffect> FREEZE = genEffect("freezing_spell",
			() -> new FreezingSpellEffect(MobEffectCategory.BENEFICIAL, 0xff9d8ef4),
			"Spell damage also inflict freezing effect");

	public static final SimpleEntry<MobEffect> SHIELDING = genEffect("synchronized_shield",
			() -> new ShieldingEffect(MobEffectCategory.BENEFICIAL, 0xffffbd0c),
			"When player heals, gain absorption as well");

	public static final SimpleEntry<MobEffect> WILDEN = genEffect("wilden",
			() -> new WildenEffect(MobEffectCategory.BENEFICIAL, 0xff935aab),
			"Gain max mana, mana restoration, and spell damage");


	private static <T extends MobEffect> SimpleEntry<MobEffect> genEffect(String name, NonNullSupplier<T> sup, String desc) {
		return new SimpleEntry<>(ArsDelight.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId).register());
	}

	public static void register() {

	}

}
