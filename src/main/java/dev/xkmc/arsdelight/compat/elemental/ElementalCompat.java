package dev.xkmc.arsdelight.compat.elemental;

import alexthw.ars_elemental.registry.ModItems;
import alexthw.ars_elemental.registry.ModPotions;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.content.jelly.JellyBlock;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntity;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.TagGen;
import dev.xkmc.arsdelight.init.food.ADPie;
import dev.xkmc.arsdelight.init.food.EffectEntry;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import vectorwing.farmersdelight.common.item.FuelItem;

public class ElementalCompat {

	public static final BlockEntry<Block> FLASHPINE_CRATE;
	public static final ItemEntry<FuelItem> FLASH_BARK;
	public static final BlockEntry<JellyBlock> FLASHPINE_JELLY;
	public static final ADPie FLASHPINE_PIE;

	public static final SimpleEntry<MobEffect> LIGHTNING_CURSE;

	static {
		LIGHTNING_CURSE = new SimpleEntry<>(ArsDelight.REGISTRATE.effect("lightning_curse", LightningCurseEffect::new,
				"Inflict lighting lure to your spell attack targets"
		).lang(MobEffect::getDescriptionId).register());

		FLASHPINE_CRATE = ADBlocks.crate("flashpine_crate");
		FLASH_BARK = ADItems.ingredient("flashing_bark", p -> new FuelItem(p, 200))
				.asOptional().tag(TagGen.BARKS).register();
		FLASHPINE_JELLY = ADJellys.jelly("flashpine_jelly", () -> ADJellys.resolve(ModItems.FLASHPINE_FOOD, 1, 1));
		FLASHPINE_PIE = new ADPie("flashpine_pie", false, new EffectEntry(LIGHTNING_CURSE, 200));
		AEFood.register();
		NeoForge.EVENT_BUS.register(ElementalCompat.class);
	}

	@SubscribeEvent
	public static void spellDamage(SpellDamageEvent.Post event) {
		var ins = event.caster.getEffect(LIGHTNING_CURSE);
		if (ins != null && event.target instanceof LivingEntity le) {
			le.addEffect(new MobEffectInstance(ModPotions.LIGHTNING_LURE, 20, ins.getAmplifier()));
		}
	}

	public static void register(BlockEntityBuilder<JellyBlockEntity, L2Registrate> builder) {
		builder.validBlock(FLASHPINE_JELLY);
	}

}
