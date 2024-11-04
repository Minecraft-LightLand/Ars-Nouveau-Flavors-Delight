package dev.xkmc.arsdelight.init.food;

import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.compat.diet.DietTagGen;
import dev.xkmc.arsdelight.content.item.ADFoodItem;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.TagGen;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.List;
import java.util.Locale;

public enum ADFood implements ItemLike {

	WILDEN_MEAT(FoodType.MEAT, 6, 0.4f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 600, 0, 0.25f)
	), TagGen.RAW_WILDEN_MEAT, DietTagGen.PROTEINS.tag),
	GRILLED_WILDEN_MEAT(FoodType.MEAT, 10, 0.7f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 600, 0, 0.5f)
	), TagGen.COOKED_WILDEN_MEAT, DietTagGen.PROTEINS.tag),
	WILDEN_MEAT_SLICE(FoodType.FAST_MEAT, 3, 0.4f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 300, 0, 0.25f)
	), TagGen.RAW_WILDEN_MEAT, DietTagGen.PROTEINS.tag),
	GRILLED_WILDEN_MEAT_SLICE(FoodType.FAST_MEAT, 5, 0.7f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 300, 0, 0.5f)
	), TagGen.COOKED_WILDEN_MEAT, DietTagGen.PROTEINS.tag),

	CHIMERA_MEAT(FoodType.MEAT, 12, 0.4f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 600, 1, 0.5f)
	), TagGen.RAW_CHIMERA, DietTagGen.PROTEINS.tag),
	GRILLED_CHIMERA_MEAT(FoodType.MEAT, 18, 0.7f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 600, 1)
	), TagGen.COOKED_CHIMERA, DietTagGen.PROTEINS.tag),
	CHIMERA_MEAT_SLICE(FoodType.FAST_MEAT, 6, 0.4f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 300, 1, 0.5f)
	), TagGen.RAW_CHIMERA, DietTagGen.PROTEINS.tag),
	GRILLED_CHIMERA_MEAT_SLICE(FoodType.FAST_MEAT, 9, 0.7f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 300, 1)
	), TagGen.COOKED_CHIMERA, DietTagGen.PROTEINS.tag),

	SOURCE_BERRY_COOKIE(FoodType.FAST, 2, 0.8f, List.of(
			new EffectEntry(ModPotions.MANA_REGEN_EFFECT, 200, 0)
	), DietTagGen.SUGARS.tag),
	SOURCE_BERRY_PIE_SLICE(FoodType.FAST, 3, 0.8f, List.of(
			new EffectEntry(ModPotions.MANA_REGEN_EFFECT, 300, 1)
	), DietTagGen.SUGARS.tag),
	SOURCE_BERRY_CUPCAKE(FoodType.FAST, 6, 0.8f, List.of(
			new EffectEntry(ModPotions.MANA_REGEN_EFFECT, 1200, 2)
	), DietTagGen.SUGARS.tag),
	ARCH_SAUCE(FoodType.BOWL, 4, 0.4f, List.of(
			new EffectEntry(ModPotions.MANA_REGEN_EFFECT, 1200, 1)
	), DietTagGen.FRUITS.tag),
	WILDEN_SAUCE(FoodType.MEAT_PLATE, 6, 0.8f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 600)
	), DietTagGen.PROTEINS.tag),
	ARCH_SOUP(FoodType.BOWL, 4, 0.4f, List.of(
			new EffectEntry(ModPotions.MANA_REGEN_EFFECT, 1200),
			new EffectEntry(ModEffects.COMFORT, 1200)
	), DietTagGen.VEGETABLES.tag),
	/*
	WILDEN_STEW(FoodType.MEAT_PLATE, 12, 0.8f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 3600, 0),
			new EffectEntry(ModEffects.COMFORT, 3600),
			new EffectEntry(ModEffects.NOURISHMENT, 3600)
	), DietTagGen.PROTEINS.tag),*/
	/*WILDEN_SKEWER(FoodType.MEAT_STICK, 8, 0.8f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 2400, 0),
			new EffectEntry(ModEffects.NOURISHMENT, 2400)
	), DietTagGen.PROTEINS.tag),*/
	BOWL_OF_WILDEN_SALAD(FoodType.MEAT_PLATE, 12, 0.8f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 2400, 1),
			new EffectEntry(ModEffects.NOURISHMENT, 2400)
	), DietTagGen.PROTEINS.tag),
	HORN_ROLL(FoodType.HORNED_MEAT, 12, 0.8f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 2400, 2),
			new EffectEntry(ModEffects.NOURISHMENT, 2400)
	), DietTagGen.PROTEINS.tag),
	BOWL_OF_HONEY_GLAZED_CHIMERA(FoodType.MEAT_PLATE, 18, 0.8f, List.of(
			new EffectEntry(ADEffects.WILDEN::get, 3600, 2),
			new EffectEntry(ModEffects.NOURISHMENT, 3600),
			new EffectEntry(ModEffects.COMFORT, 3600)
	)),

	MENDOSTEEN_TEA(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(ModPotions.RECOVERY_EFFECT, 1800),
			new EffectEntry(ADEffects.FLOURISH::get, 1200)
	), DietTagGen.FRUITS.tag),
	BASTION_TEA(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(ModPotions.DEFENCE_EFFECT, 1800),
			new EffectEntry(ADEffects.SHIELDING::get, 1200)
	), DietTagGen.FRUITS.tag),
	BOMBEGRANTE_TEA(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(ADEffects.BLAST_RES::get, 2400, 1)
	), DietTagGen.FRUITS.tag),
	FROSTAYA_TEA(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(ADEffects.FREEZE::get, 4800)
	), DietTagGen.FRUITS.tag),
	UNSTABLE_COCKTAIL(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(ModPotions.BLAST_EFFECT, 200, 4)
	), DietTagGen.FRUITS.tag),

	MENDOSTEEN_HORNBEER(FoodType.HORNED_DRINK, 0, 0, List.of(
			new EffectEntry(ModPotions.RECOVERY_EFFECT, 1200, 1),
			new EffectEntry(ADEffects.FLOURISH::get, 1200, 1)
	), DietTagGen.FRUITS.tag),
	BASTION_HORNBEER(FoodType.HORNED_DRINK, 0, 0, List.of(
			new EffectEntry(ModPotions.DEFENCE_EFFECT, 1200, 1),
			new EffectEntry(ADEffects.SHIELDING::get, 1200, 1)
	), DietTagGen.FRUITS.tag),
	BOMBEGRANTE_HORNBEER(FoodType.HORNED_DRINK, 0, 0, List.of(
			new EffectEntry(ADEffects.BLAST_RES::get, 1200, 3)
	), DietTagGen.FRUITS.tag),
	FROSTAYA_HORNBEER(FoodType.HORNED_DRINK, 0, 0, List.of(
			new EffectEntry(ADEffects.FREEZE::get, 2400, 1)
	), DietTagGen.FRUITS.tag),

	ACTIVATED_MENDOSTEEN_JAM(FoodType.JELLY, 0, 0, List.of(
			new EffectEntry(ModPotions.RECOVERY_EFFECT, 1200),
			new EffectEntry(ADEffects.FLOURISH::get, 600)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),
	ACTIVATED_BASTION_JAM(FoodType.JELLY, 0, 0, List.of(
			new EffectEntry(ModPotions.DEFENCE_EFFECT, 1200),
			new EffectEntry(ADEffects.SHIELDING::get, 600)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),
	NEUTRALIZED_BOMBEGRANTE_JAM(FoodType.JELLY, 0, 0, List.of(
			new EffectEntry(ADEffects.BLAST_RES::get, 1200, 1)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),
	NEUTRALIZED_FROSTAYA_JAM(FoodType.JELLY, 0, 0, List.of(
			new EffectEntry(ADEffects.FREEZE::get, 2400)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),

	MENDOSTEEN_CHICKEN(FoodType.MEAT_PLATE, 10, 0.8f, List.of(
			new EffectEntry(ModPotions.RECOVERY_EFFECT, 2400),
			new EffectEntry(ADEffects.FLOURISH::get, 2400),
			new EffectEntry(ModEffects.COMFORT, 3600)
	), DietTagGen.FRUITS.tag, DietTagGen.PROTEINS.tag),
	BASTION_PORK(FoodType.MEAT_PLATE, 14, 0.8f, List.of(
			new EffectEntry(ModPotions.DEFENCE_EFFECT, 2400),
			new EffectEntry(ADEffects.SHIELDING::get, 2400),
			new EffectEntry(ModEffects.COMFORT, 3600)
	), DietTagGen.FRUITS.tag, DietTagGen.PROTEINS.tag),
	BOMBEGRANTE_STEAK(FoodType.MEAT_PLATE, 16, 0.8f, List.of(
			new EffectEntry(ADEffects.BLAST_RES::get, 3600, 1),
			new EffectEntry(ModEffects.NOURISHMENT, 3600)
	), DietTagGen.FRUITS.tag, DietTagGen.PROTEINS.tag),
	FROSTAYA_MUTTON(FoodType.MEAT_PLATE, 16, 0.8f, List.of(
			new EffectEntry(ADEffects.FREEZE::get, 3600),
			new EffectEntry(ModEffects.NOURISHMENT, 3600)
	), DietTagGen.FRUITS.tag, DietTagGen.PROTEINS.tag),
	;

	private final String name;
	public final FoodType type;
	public final ItemEntry<ADFoodItem> item;

	@SafeVarargs
	ADFood(FoodType type, int nut, float sat, List<EffectEntry> effs, TagKey<Item>... tags) {
		this.name = name().toLowerCase(Locale.ROOT);
		this.type = type;
		String tex = (nut == 0 ? "item/drink/" : "item/food/") + name;
		this.item = ArsDelight.REGISTRATE.item(name, p -> this.build(p, nut, sat, effs))
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc(tex)))
				.lang(ADItems.toEnglishName(name))
				.tag(tags)
				.register();
	}

	private ADFoodItem build(Item.Properties prop, int nut, float sat, List<EffectEntry> effs) {
		var builder = new FoodProperties.Builder();
		builder.nutrition(nut).saturationMod(sat);
		for (var e : effs) {
			builder.effect(e::getEffect, e.chance());
		}
		return type.build(prop, builder);
	}

	public ItemStack asStack() {
		return item.asStack();
	}

	public ADFoodItem get() {
		return item.get();
	}

	@Override
	public Item asItem() {
		return item.get();
	}

	public static void register() {
	}

}
