package dev.xkmc.arsdelight.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.arsdelight.content.effect.*;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ADEffects {

	public static final RegistryEntry<BlastResistanceEffect> BLAST_RES = genEffect("blast_resistance",
			() -> new BlastResistanceEffect(MobEffectCategory.BENEFICIAL, 0xffffffff),
			"Reduce incoming explosion damage");

	public static final RegistryEntry<FlourishingEffect> FLOURISH = genEffect("flourishing",
			() -> new FlourishingEffect(MobEffectCategory.BENEFICIAL, 0xffffffff),
			"When player heals, recover mana as well");

	public static final RegistryEntry<FreezingSpellEffect> FREEZE = genEffect("freezing_spell",
			() -> new FreezingSpellEffect(MobEffectCategory.BENEFICIAL, 0xffffffff),
			"Spell damage also inflict freezing effect");

	public static final RegistryEntry<ShieldingEffect> SHIELDING = genEffect("synchronized_shield",
			() -> new ShieldingEffect(MobEffectCategory.BENEFICIAL, 0xffffffff),
			"When player heals, gain absorption as well");

	public static final RegistryEntry<WildenEffect> WILDEN = genEffect("wilden",
			() -> new WildenEffect(MobEffectCategory.BENEFICIAL, 0xffffffff),
			"Gain max mana, mana restoration, and spell damage");


	private static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup, String desc) {
		return ArsDelight.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId).register();
	}

	public static void register() {

	}

}
