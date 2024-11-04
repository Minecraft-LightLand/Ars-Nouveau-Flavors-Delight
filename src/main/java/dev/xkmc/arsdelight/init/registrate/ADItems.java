package dev.xkmc.arsdelight.init.registrate;

import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.content.item.EnchantersKnife;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import net.minecraft.world.item.Tiers;
import org.apache.commons.lang3.StringUtils;
import vectorwing.farmersdelight.common.item.FuelItem;
import vectorwing.farmersdelight.common.tag.CommonTags;
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
		FLOURISHING_BARK = ArsDelight.REGISTRATE.item("flourishing_bark", p -> new FuelItem(p, 200)).register();
		VEXING_BARK = ArsDelight.REGISTRATE.item("vexing_bark", p -> new FuelItem(p, 200)).register();
		CASCADING_BARK = ArsDelight.REGISTRATE.item("cascading_bark", p -> new FuelItem(p, 200)).register();
		BLAZING_BARK = ArsDelight.REGISTRATE.item("blazing_bark", p -> new FuelItem(p, 400)).register();
		HORN_POWDER = ArsDelight.REGISTRATE.item("wilden_horn_powder", ModItem::new).register();
		SPIKE_POWDER = ArsDelight.REGISTRATE.item("wilden_spike_powder", ModItem::new).register();
		CHIMERA_HORN = ArsDelight.REGISTRATE.item("chimera_horn", ModItem::new).register();
		KNIFE = ArsDelight.REGISTRATE.item("enchanters_knife", p -> new EnchantersKnife(Tiers.NETHERITE, 1, -2F))
				.model((ctx, pvd) -> {
				}).tag(CommonTags.TOOLS_KNIFE, ModTags.KNIVES).register();
	}

	public static String toEnglishName(String internalName) {
		return Arrays.stream(internalName.split("_"))
				.map(e -> SMALL_WORDS.contains(e) ? e : StringUtils.capitalize(e))
				.collect(Collectors.joining(" "));
	}

	public static void register() {
		ADBlocks.register();
		ADFood.register();
	}

}
