package dev.xkmc.arsdelight.compat.archwood;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.compat.diet.DietTagGen;
import dev.xkmc.arsdelight.content.item.ADFoodItem;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.EffectEntry;
import dev.xkmc.arsdelight.init.food.FoodType;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Locale;

public enum AWFood implements ItemLike {
	DAWNBERRY_TEA(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(MobEffects.FIRE_RESISTANCE, 4800)
	), DietTagGen.FRUITS.tag),
	DAWNBERRY_HORNBEER(FoodType.HORNED_DRINK, 0, 0, List.of(
			new EffectEntry(MobEffects.FIRE_RESISTANCE, 9600)
	), DietTagGen.FRUITS.tag),
	NEUTRALIZED_DAWNBERRY_JAM(FoodType.JELLY, 0, 0, List.of(
			new EffectEntry(MobEffects.FIRE_RESISTANCE, 2400)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),

	LIGHTCHEE_TEA(FoodType.DRINK, 0, 0, List.of(
			new EffectEntry(MobEffects.REGENERATION, 4800)
	), DietTagGen.FRUITS.tag),
	LIGHTCHEE_HORNBEER(FoodType.HORNED_DRINK, 0, 0, List.of(
			new EffectEntry(MobEffects.REGENERATION, 2400, 1)
	), DietTagGen.FRUITS.tag),
	NEUTRALIZED_LIGHTCHEE_JAM(FoodType.JELLY, 0, 0, List.of(
			new EffectEntry(MobEffects.REGENERATION, 2400)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),

	SKITTLE_STEW(FoodType.BOWL, 10, 1.2f, List.of(
			new EffectEntry(MobEffects.REGENERATION, 4800)
	), DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag),
	;

	private final String name;
	public final FoodType type;
	public final ItemEntry<ADFoodItem> item;
	private final List<EffectEntry> effs;
	private final TagKey<Item>[] tags;

	@SafeVarargs
	AWFood(FoodType type, int nut, float sat, List<EffectEntry> effs, TagKey<Item>... tags) {
		this.name = name().toLowerCase(Locale.ROOT);
		this.type = type;
		String tex = switch (type) {
			case MEAT, FAST_MEAT, MEAT_STICK -> "item/meat/";
			case JELLY, DRINK, HORNED_DRINK -> "item/drink/";
			default -> "item/food/";
		} + name;
		this.item = ArsDelight.REGISTRATE.item(name, p -> this.build(p, nut, sat, effs))
				.model((ctx, pvd) -> type.model(pvd, ctx, pvd.modLoc(tex)))
				.lang(ADItems.toEnglishName(name))
				.asOptional().tag(tags)
				.register();
		this.effs = effs;
		this.tags = tags;
	}

	private ADFoodItem build(Item.Properties prop, int nut, float sat, List<EffectEntry> effs) {
		var builder = new FoodProperties.Builder();
		builder.nutrition(nut).saturationModifier(sat);
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

	public ResourceLocation id() {
		return item.getId();
	}

	public static void register() {
	}

}
