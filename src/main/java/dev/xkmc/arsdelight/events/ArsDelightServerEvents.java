package dev.xkmc.arsdelight.events;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.ADModConfig;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArsDelightServerEvents {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerHeal(LivingHealEvent event) {
		var e = event.getEntity();
		var flourish = e.getEffect(ADEffects.FLOURISH.get());
		if (flourish != null) {
			var cap = CapabilityRegistry.getMana(e).resolve();
			int factor = 1 << flourish.getAmplifier();
			if (cap.isPresent()) {
				double max = cap.get().getMaxMana();
				double maxhp = e.getMaxHealth();
				double gain = event.getAmount() / maxhp * max * factor;
				cap.get().addMana(gain);
			}
		}
		var shielding = e.getEffect(ADEffects.SHIELDING.get());
		if (shielding != null) {
			double factor = 1 << shielding.getAmplifier();
			double max = ADModConfig.COMMON.maxShieldingAbsorption.get() * factor;
			double old = e.getAbsorptionAmount();
			double newAb = Math.min(max, old + event.getAmount() * factor);
			e.setAbsorptionAmount((float) newAb);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onDamage(LivingDamageEvent event) {
		if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
			var ins = event.getEntity().getEffect(ADEffects.BLAST_RES.get());
			if (ins != null) {
				float res = Math.max(0, 1 - (ins.getAmplifier() + 1) * 0.2f);
				event.setAmount(event.getAmount() * res);
			}
		}
	}

	@SubscribeEvent
	public static void spellDamagePre(SpellDamageEvent.Pre event) {
		var ins = event.caster.getEffect(ADEffects.WILDEN.get());
		if (ins != null) {
			double factor = ADModConfig.COMMON.wildenSpellDamageBonus.get();
			event.damage *= 1 + (ins.getAmplifier() + 1) * (float) factor;
		}
	}

	@SubscribeEvent
	public static void spellDamage(SpellDamageEvent.Post event) {
		var ins = event.caster.getEffect(ADEffects.FREEZE.get());
		if (ins != null && event.target instanceof LivingEntity le) {
			le.addEffect(new MobEffectInstance(ModPotions.FREEZING_EFFECT.get(), ins.getDuration(), ins.getAmplifier()));
		}
	}

	@SubscribeEvent
	public static void maxManaCalc(MaxManaCalcEvent event) {
		var ins = event.getEntity().getEffect(ADEffects.WILDEN.get());
		if (ins != null) {
			double config = ADModConfig.COMMON.wildenMaxManaBonus.get();
			double factor = 1 + (ins.getAmplifier() + 1) * config;
			event.setMax((int) (event.getMax() * factor));
		}
	}

	@SubscribeEvent
	public static void ManaRegenCalc(ManaRegenCalcEvent event) {
		var ins = event.getEntity().getEffect(ADEffects.WILDEN.get());
		if (ins != null) {
			double config = ADModConfig.COMMON.wildenManaRegenBonus.get();
			double factor = 1 + (ins.getAmplifier() + 1) * config;
			event.setRegen(event.getRegen() * factor);
		}
	}

}
