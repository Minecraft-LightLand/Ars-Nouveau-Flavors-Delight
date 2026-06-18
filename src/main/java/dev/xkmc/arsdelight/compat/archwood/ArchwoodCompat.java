package dev.xkmc.arsdelight.compat.archwood;

import com.alexthw.archwood_good.registry.AWGItemRegistry;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.content.jelly.JellyBlock;
import dev.xkmc.arsdelight.init.data.TagGen;
import dev.xkmc.arsdelight.init.food.ADPie;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import vectorwing.farmersdelight.common.item.FuelItem;

public class ArchwoodCompat {

	public static final BlockEntry<Block> DAWNBERRY_CRATE, LIGHTCHEE_CRATE;
	public static final BlockEntry<JellyBlock> DAWNBERRY_JELLY, LIGHTCHEE_JELLY;
	public static final ADPie DAWNBERRY_PIE, LIGHTCHEE_PIE;
	public static final ItemEntry<FuelItem> DAWN_BARK, BLEAK_BARK, FADING_BARK;

	static {
		DAWNBERRY_CRATE = ADBlocks.crate("dawnberry_crate");
		LIGHTCHEE_CRATE = ADBlocks.crate("lightchee_crate");
		DAWNBERRY_JELLY = ADJellys.jelly("dawnberry_jelly", () -> new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.WITHER, 100), 1));
		LIGHTCHEE_JELLY = ADJellys.jelly("lightchee_jelly", () -> ADJellys.resolve(AWGItemRegistry.LIGHTCHEE_FOOD, 1, 1));
		DAWNBERRY_PIE = new ADPie("dawnberry_pie", false);
		LIGHTCHEE_PIE = new ADPie("lightchee_pie", false);
		DAWN_BARK = ADItems.ingredient("dawn_bark", p -> new FuelItem(p, 200)).asOptional().tag(TagGen.BARKS).register();
		BLEAK_BARK = ADItems.ingredient("bleak_bark", p -> new FuelItem(p, 200)).asOptional().tag(TagGen.BARKS).register();
		FADING_BARK = ADItems.ingredient("fading_bark", p -> new FuelItem(p, 200)).asOptional().tag(TagGen.BARKS).register();

		AWFood.register();
		NeoForge.EVENT_BUS.register(ArchwoodCompat.class);
	}

	@SubscribeEvent
	public static void spellDamage(SpellDamageEvent.Post event) {
	}

	public static void register() {

	}

}
