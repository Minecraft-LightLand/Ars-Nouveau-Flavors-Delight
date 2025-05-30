package dev.xkmc.arsdelight.init.registrate;

import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.arsdelight.content.item.EnchantersKnife;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.TagGen;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.food.ADPie;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.client.model.generators.ModelFile;
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
		FLOURISHING_BARK = ingredient("flourishing_bark", p -> new FuelItem(p, 200)).tag(TagGen.BARKS).register();
		VEXING_BARK = ingredient("vexing_bark", p -> new FuelItem(p, 200)).tag(TagGen.BARKS).register();
		CASCADING_BARK = ingredient("cascading_bark", p -> new FuelItem(p, 200)).tag(TagGen.BARKS).register();
		BLAZING_BARK = ingredient("blazing_bark", p -> new FuelItem(p, 400)).tag(TagGen.BARKS).register();
		HORN_POWDER = ingredient("wilden_horn_powder", ModItem::new).register();
		SPIKE_POWDER = ingredient("wilden_spike_powder", ModItem::new).register();
		CHIMERA_HORN = ArsDelight.REGISTRATE.item("chimera_horn", ModItem::new)
				.model((ctx, pvd) ->
						mug(pvd, ctx, pvd.modLoc("item/ingredient/" + ctx.getName())))
				.register();

		KNIFE = ArsDelight.REGISTRATE.item("enchanters_knife", p -> new EnchantersKnife(Tiers.NETHERITE, 1, -2F))
				.model((ctx, pvd) -> {
				}).tag(ForgeTags.TOOLS_KNIVES, ModTags.KNIVES, ModTags.FLAT_ON_CUTTING_BOARD)
				.lang("Enchanter's Knife").register();
	}

	public static String toEnglishName(String internalName) {
		return Arrays.stream(internalName.split("_"))
				.map(e -> SMALL_WORDS.contains(e) ? e : StringUtils.capitalize(e))
				.collect(Collectors.joining(" ")).replace("grante", "granate");
	}

	private static <T extends Item> ItemBuilder<T, L2Registrate> ingredient(String id, NonNullFunction<Item.Properties, T> factory) {
		return ArsDelight.REGISTRATE.item(id, factory).model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/ingredient/" + ctx.getName())));
	}

	public static void mug(RegistrateItemModelProvider pvd, DataGenContext<Item, ? extends Item> ctx, ResourceLocation tex) {
		pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile(
						new ResourceLocation("farmersdelight", "item/mug")))
				.texture("layer0", tex);
	}

	public static void register() {
		ADBlocks.register();
		ADJellys.register();
		ADPie.register();
		ADFood.register();
	}

}
