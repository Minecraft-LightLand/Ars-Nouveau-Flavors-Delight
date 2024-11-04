package dev.xkmc.arsdelight.init.registrate;

import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.arsdelight.content.item.EnchantersKnife;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import org.apache.commons.lang3.StringUtils;
import vectorwing.farmersdelight.common.item.FuelItem;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ADItems {

	private static final Set<String> SMALL_WORDS = Set.of("of", "the", "with");

	public static final ItemEntry<FuelItem> FLOURISHING_BARK, VEXING_BARK, CASCADING_BARK, BLAZING_BARK;
	public static final ItemEntry<ModItem> HORN_POWDER, SPIKE_POWDER, CHIMERA_HORN;
	public static final ItemEntry<EnchantersKnife> KNIFE;

	static {
		FLOURISHING_BARK = ingredient("flourishing_bark", p -> new FuelItem(p, 200)).register();
		VEXING_BARK = ingredient("vexing_bark", p -> new FuelItem(p, 200)).register();
		CASCADING_BARK = ingredient("cascading_bark", p -> new FuelItem(p, 200)).register();
		BLAZING_BARK = ingredient("blazing_bark", p -> new FuelItem(p, 400)).register();
		HORN_POWDER = ingredient("wilden_horn_powder", ModItem::new).register();
		SPIKE_POWDER = ingredient("wilden_spike_powder", ModItem::new).register();
		CHIMERA_HORN = ingredient("chimera_horn", ModItem::new).register();

		KNIFE = ArsDelight.REGISTRATE.item("enchanters_knife", p -> new EnchantersKnife(Tiers.NETHERITE, 1, -2F))
				.model((ctx, pvd) -> {
				}).tag(ForgeTags.TOOLS_KNIVES, ModTags.KNIVES)
				.lang("Enchanter's Knife").register();
	}

	public static String toEnglishName(String internalName) {
		return Arrays.stream(internalName.split("_"))
				.map(e -> SMALL_WORDS.contains(e) ? e : StringUtils.capitalize(e))
				.collect(Collectors.joining(" "));
	}

	private static <T extends Item> ItemBuilder<T, L2Registrate> ingredient(String id, NonNullFunction<Item.Properties, T> factory) {
		return ArsDelight.REGISTRATE.item(id, factory).model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/ingredient/" + ctx.getName())));
	}

	public static void register() {
		ADBlocks.register();
		ADFood.register();
	}

}
