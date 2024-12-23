package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModItems;

public class TagGen {

	public static final TagKey<Item> RAW_CHIMERA = item("raw_chimera");
	public static final TagKey<Item> COOKED_CHIMERA = item("cooked_chimera");

	public static final TagKey<Item> RAW_WILDEN_MEAT = item("raw_wilden_meat");
	public static final TagKey<Item> COOKED_WILDEN_MEAT = item("cooked_wilden_meat");

	public static final TagKey<Item> JELLY = item("jelly");
	public static final TagKey<Item> BARKS = item("barks");
	public static final TagKey<Item> LEAVES = item("leaves");
	public static final TagKey<Item> FDBARKS = fdItem("barks");

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		var builder = pvd.addTag(ItemTagProvider.MAGIC_FOOD);
		for (var e : ADFood.values()) {
			builder.add(e.asItem());
		}
		pvd.addTag(FDBARKS).addTag(BARKS).add(ModItems.TREE_BARK.get());
		pvd.addTag(LEAVES).add(
				BlockRegistry.BLAZING_LEAVES.asItem(),
				BlockRegistry.FLOURISHING_LEAVES.asItem(),
				BlockRegistry.VEXING_LEAVES.asItem(),
				BlockRegistry.CASCADING_LEAVE.asItem()
		);

	}

	private static TagKey<Item> item(String id) {
		return ItemTags.create(ArsDelight.loc(id));
	}

	private static TagKey<Item> fdItem(String id) {
		return ItemTags.create(new ResourceLocation(FarmersDelight.MODID, id));
	}

}
