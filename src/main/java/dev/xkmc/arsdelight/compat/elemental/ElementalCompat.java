package dev.xkmc.arsdelight.compat.elemental;

import alexthw.ars_elemental.ArsElemental;
import alexthw.ars_elemental.registry.ModItems;
import alexthw.ars_elemental.registry.ModPotions;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.arsdelight.content.jelly.JellyBlock;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntity;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.TagGen;
import dev.xkmc.arsdelight.init.food.ADPie;
import dev.xkmc.arsdelight.init.food.EffectEntry;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vectorwing.farmersdelight.common.item.FuelItem;

public class ElementalCompat {

	public static final BlockEntry<Block> FLASHPINE_CRATE;
	public static final ItemEntry<FuelItem> FLASH_BARK;
	public static final BlockEntry<JellyBlock> FLASHPINE_JELLY;
	public static final ADPie FLASHPINE_PIE;

	public static final RegistryEntry<LightningCurseEffect> LIGHTNING_CURSE;

	static {
		LIGHTNING_CURSE = ArsDelight.REGISTRATE.effect("lightning_curse", LightningCurseEffect::new,
				"Inflict lighting lure to your spell attack targets"
		).lang(MobEffect::getDescriptionId).register();

		FLASHPINE_CRATE = ADBlocks.crate(ArsElemental.MODID, "flashpine_crate");
		FLASH_BARK = ADItems.ingredient("flashing_bark", p -> new FuelItem(p, 200)).register();
		FLASHPINE_JELLY = ADJellys.jelly(ArsElemental.MODID, "flashpine_jelly",
				() -> ADJellys.resolve(ModItems.FLASHPINE_FOOD, 1, 1));
		FLASHPINE_PIE = new ADPie(ArsElemental.MODID, "flashpine_pie", false,
				new EffectEntry(LIGHTNING_CURSE::get, 200));

		TagGen.putItem(ArsElemental.MODID, FLASH_BARK, TagGen.BARKS);
		AEFood.register();
		MinecraftForge.EVENT_BUS.register(ElementalCompat.class);
	}

	@SubscribeEvent
	public static void spellDamage(SpellDamageEvent.Post event) {
		var ins = event.caster.getEffect(LIGHTNING_CURSE.get());
		if (ins != null && event.target instanceof LivingEntity le) {
			le.addEffect(new MobEffectInstance(ModPotions.LIGHTNING_LURE.get(), 20, ins.getAmplifier()));
		}
	}

	public static void register(BlockEntityBuilder<JellyBlockEntity, L2Registrate> builder) {
		builder.validBlock(FLASHPINE_JELLY);
	}

}
