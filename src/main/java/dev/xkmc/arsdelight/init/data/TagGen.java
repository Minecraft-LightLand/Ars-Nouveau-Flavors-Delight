package dev.xkmc.arsdelight.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagGen {

	public static final TagKey<Item> RAW_CHIMERA = item("raw_chimera");
	public static final TagKey<Item> COOKED_CHIMERA = item("cooked_chimera");

	public static final TagKey<Item> RAW_WILDEN_MEAT = item("raw_wilden_meat");
	public static final TagKey<Item> COOKED_WILDEN_MEAT = item("cooked_wilden_meat");

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
	}

	private static TagKey<Item> item(String id){
		return ItemTags.create(ArsDelight.loc(id));
	}

}
