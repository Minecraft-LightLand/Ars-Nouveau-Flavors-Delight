package dev.xkmc.arsdelight.events;

import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.ADDoc;
import dev.xkmc.arsdelight.init.data.ADModConfig;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

@EventBusSubscriber(modid = ArsDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ArsDelightServerEvents {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerHeal(LivingHealEvent event) {
		var e = event.getEntity();
		var flourish = e.getEffect(ADEffects.FLOURISH);
		if (flourish != null) {
			var cap = CapabilityRegistry.getMana(e);
			int factor = 1 << flourish.getAmplifier();
			if (cap != null) {
				double max = cap.getMaxMana();
				double maxhp = e.getMaxHealth();
				double gain = event.getAmount() / maxhp * max * factor;
				cap.addMana(gain);
			}
		}
		var shielding = e.getEffect(ADEffects.SHIELDING);
		if (shielding != null) {
			double factor = 1 << shielding.getAmplifier();
			double max = ADModConfig.SERVER.maxShieldingAbsorption.get() * factor;
			double old = e.getAbsorptionAmount();
			double newAb = Math.min(max, old + event.getAmount() * factor);
			e.setAbsorptionAmount((float) newAb);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onDamage(LivingDamageEvent.Pre event) {
		if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
			var ins = event.getEntity().getEffect(ADEffects.BLAST_RES);
			if (ins != null) {
				float res = Math.max(0, 1 - (ins.getAmplifier() + 1) * 0.2f);
				event.setNewDamage(event.getNewDamage() * res);
			}
		}
	}

	@SubscribeEvent
	public static void spellDamagePre(SpellDamageEvent.Pre event) {
		var ins = event.caster.getEffect(ADEffects.WILDEN);
		if (ins != null) {
			double factor = ADModConfig.SERVER.wildenSpellDamageBonus.get();
			event.damage *= 1 + (ins.getAmplifier() + 1) * (float) factor;
		}
	}

	@SubscribeEvent
	public static void spellDamage(SpellDamageEvent.Post event) {
		var ins = event.caster.getEffect(ADEffects.FREEZE);
		if (ins != null && event.target instanceof LivingEntity le) {
			le.addEffect(new MobEffectInstance(ModPotions.FREEZING_EFFECT, ins.getDuration(), ins.getAmplifier()));
		}
	}

	@SubscribeEvent
	public static void maxManaCalc(MaxManaCalcEvent event) {
		var ins = event.getEntity().getEffect(ADEffects.WILDEN);
		if (ins != null) {
			double config = ADModConfig.SERVER.wildenMaxManaBonus.get();
			double factor = 1 + (ins.getAmplifier() + 1) * config;
			event.setMax((int) (event.getMax() * factor));
		}
	}

	@SubscribeEvent
	public static void ManaRegenCalc(ManaRegenCalcEvent event) {
		var ins = event.getEntity().getEffect(ADEffects.WILDEN);
		if (ins != null) {
			double config = ADModConfig.SERVER.wildenManaRegenBonus.get();
			double factor = 1 + (ins.getAmplifier() + 1) * config;
			event.setRegen(event.getRegen() * factor);
		}
	}

	@SubscribeEvent
	public static void reloadDoc(ReloadDocumentationEvent.AddEntries event) {
		ADDoc.addPages();
	}

}
