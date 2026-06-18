package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TagGen {

	public static final TagKey<Item> RAW_CHIMERA = item("raw_chimera");
	public static final TagKey<Item> COOKED_CHIMERA = item("cooked_chimera");

	public static final TagKey<Item> RAW_WILDEN_MEAT = item("raw_wilden_meat");
	public static final TagKey<Item> COOKED_WILDEN_MEAT = item("cooked_wilden_meat");

	public static final TagKey<Item> JELLY = item("jelly");
	public static final TagKey<Item> BARKS = item("barks");
	public static final TagKey<Item> LEAVES = item("leaves");
	public static final TagKey<Item> FDBARKS = fdItem("barks");

	private static final List<Consumer<RegistrateItemTagsProvider>> ITEM_TAGS = new ArrayList<>();
	private static final List<Consumer<RegistrateTagsProvider.IntrinsicImpl<Block>>> BLOCK_TAGS = new ArrayList<>();

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(ModTags.Blocks.FEASTS)
				.add(ADBlocks.CHIMERA.get(), ADBlocks.SALAD.get());
		pvd.addTag(ModTags.Blocks.CABINETS_WOODEN).add(ADBlocks.ARCHWOOD_CABINET.get());
		pvd.addTag(ModTags.Blocks.CABINETS).add(ADBlocks.ARCHWOOD_CABINET.get());

		for (var e : BLOCK_TAGS) e.accept(pvd);
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

		pvd.addTag(CommonTags.Items.TOOLS_KNIVES).add(ADItems.KNIFE.get());
		pvd.addTag(ModTags.Items.KNIVES).add(ADItems.KNIFE.get());
		pvd.addTag(ModTags.Items.FLAT_ON_CUTTING_BOARD).add(ADItems.KNIFE.get());

		pvd.addTag(ModTags.Items.CABINETS_WOODEN).add(ADBlocks.ARCHWOOD_CABINET.asItem());
		pvd.addTag(ModTags.Items.CABINETS).add(ADBlocks.ARCHWOOD_CABINET.asItem());

		for (var e : ITEM_TAGS) e.accept(pvd);
	}

	private static TagKey<Item> item(String id) {
		return ItemTags.create(ArsDelight.loc(id));
	}

	private static TagKey<Item> fdItem(String id) {
		return ItemTags.create(new ResourceLocation(FarmersDelight.MODID, id));
	}

	@SafeVarargs
	public static void putItem(String modid, ItemEntry<?> item, TagKey<Item>... tags) {
		for (var tag : tags)
			if (modid.equals(ArsDelight.MODID))
				ITEM_TAGS.add(pvd -> pvd.addTag(tag).add(item.get()));
			else
				ITEM_TAGS.add(pvd -> pvd.addTag(tag).addOptional(item.getId()));
	}

	@SafeVarargs
	public static void putBlock(String modid, BlockEntry<?> block, TagKey<Block>... tags) {
		for (var tag : tags)
			if (modid.equals(ArsDelight.MODID))
				BLOCK_TAGS.add(pvd -> pvd.addTag(tag).add(block.get()));
			else
				BLOCK_TAGS.add(pvd -> pvd.addTag(tag).addOptional(block.getId()));
	}

}
