package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.common.util.RegistryWrapper;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.BiFunction;

public class RecipeGen {

	public static void genRecipes(RegistrateRecipeProvider pvd) {

		// simple
		{
			meat(pvd, ADFood.CHIMERA_MEAT, ADFood.GRILLED_CHIMERA_MEAT,
					ADFood.CHIMERA_MEAT_SLICE, ADFood.GRILLED_CHIMERA_MEAT_SLICE);
			meat(pvd, ADFood.WILDEN_MEAT, ADFood.GRILLED_WILDEN_MEAT,
					ADFood.WILDEN_MEAT_SLICE, ADFood.GRILLED_WILDEN_MEAT_SLICE);
			strip(pvd, ADItems.BLAZING_BARK, BlockRegistry.BLAZING_LOG, BlockRegistry.STRIPPED_AWLOG_RED,
					BlockRegistry.BLAZING_WOOD, BlockRegistry.STRIPPED_AWWOOD_RED);
			strip(pvd, ADItems.CASCADING_BARK, BlockRegistry.CASCADING_LOG, BlockRegistry.STRIPPED_AWLOG_BLUE,
					BlockRegistry.CASCADING_WOOD, BlockRegistry.STRIPPED_AWWOOD_BLUE);
			strip(pvd, ADItems.FLOURISHING_BARK, BlockRegistry.FLOURISHING_LOG, BlockRegistry.STRIPPED_AWLOG_GREEN,
					BlockRegistry.FLOURISHING_WOOD, BlockRegistry.STRIPPED_AWWOOD_GREEN);
			strip(pvd, ADItems.VEXING_BARK, BlockRegistry.VEXING_LOG, BlockRegistry.STRIPPED_AWLOG_PURPLE,
					BlockRegistry.VEXING_WOOD, BlockRegistry.STRIPPED_AWWOOD_PURPLE);

			pvd.storage(BlockRegistry.MENDOSTEEN_POD::get, RecipeCategory.MISC, ADBlocks.MENDOSTEEN_CRATE);
			pvd.storage(BlockRegistry.BASTION_POD::get, RecipeCategory.MISC, ADBlocks.BASTION_CRATE);
			pvd.storage(BlockRegistry.BOMBEGRANTE_POD::get, RecipeCategory.MISC, ADBlocks.BOMBEGRANTE_CRATE);
			pvd.storage(BlockRegistry.FROSTAYA_POD::get, RecipeCategory.MISC, ADBlocks.FROSTAYA_CRATE);
		}

		// processing
		{
			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(ItemsRegistry.WILDEN_HORN),
							Ingredient.of(ForgeTags.TOOLS_SHOVELS),
							ADItems.HORN_POWDER, 1)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(ItemsRegistry.WILDEN_SPIKE),
							Ingredient.of(ForgeTags.TOOLS_SHOVELS),
							ADItems.SPIKE_POWDER, 1)
					.build(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC,
					ADBlocks.SOURCE_BERRY_CRATE, 1)::unlockedBy, BlockRegistry.SOURCEBERRY_BUSH.asItem())
					.pattern("SSS").pattern("SBS").pattern("SSS")
					.define('S', Items.STICK)
					.define('B', BlockRegistry.SOURCEBERRY_SACK)
					.save(pvd);

			pvd.singleItemUnfinished(DataIngredient.items(ADBlocks.SOURCE_BERRY_CRATE.get()),
							RecipeCategory.MISC, () -> BlockRegistry.SOURCEBERRY_BUSH, 1, 9)
					.save(pvd, ArsDelight.loc("source_berry_unpack"));

		}

		// misc
		{
			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(ItemsRegistry.SOURCE_BERRY_PIE),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							ADFood.SOURCE_BERRY_PIE_SLICE, 4)
					.build(pvd);

			unlock(pvd, ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,
					ItemsRegistry.SOURCE_BERRY_PIE, 1)::unlockedBy, ADFood.SOURCE_BERRY_PIE_SLICE.get())
					.requires(ADFood.SOURCE_BERRY_PIE_SLICE, 4)
					.save(pvd, ArsDelight.loc("source_berry_pie"));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.FOOD,
					ADFood.SOURCE_BERRY_COOKIE, 8)::unlockedBy, BlockRegistry.SOURCEBERRY_BUSH.asItem())
					.pattern("ABA")
					.define('A', Items.WHEAT)
					.define('B', BlockRegistry.SOURCEBERRY_BUSH)
					.save(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.SOURCE_BERRY_CUPCAKE, 2, 200, 0.1f, Items.PAPER)
					.addIngredient(ADFood.ARCH_SAUCE)
					.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
					.addIngredient(ForgeTags.EGGS)
					.addIngredient(Items.WHEAT)
					.addIngredient(ForgeTags.MILK)
					.build(pvd);
		}

		// dish
		{
			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ARCH_SAUCE, 1, 200, 0.1f, Items.BOWL)
					.addIngredient(ADFood.NEUTRALIZED_FROSTAYA_JAM)
					.addIngredient(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM)
					.addIngredient(ADFood.ACTIVATED_BASTION_JAM)
					.addIngredient(ADFood.ACTIVATED_MENDOSTEEN_JAM)
					.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
					.build(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.WILDEN_SAUCE, 1, 200, 0.1f, Items.BOWL)
					.addIngredient(ADItems.HORN_POWDER)
					.addIngredient(ADItems.SPIKE_POWDER)
					.addIngredient(ItemsRegistry.WILDEN_WING)
					.build(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ARCH_SOUP, 1, 200, 0.1f, Items.BOWL)
					.addIngredient(ADItems.BLAZING_BARK)
					.addIngredient(ADItems.CASCADING_BARK)
					.addIngredient(ADItems.FLOURISHING_BARK)
					.addIngredient(ADItems.VEXING_BARK)
					.build(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADBlocks.CHIMERA, 1, 200, 0.1f, Items.BOWL)
					.addIngredient(TagGen.RAW_CHIMERA)
					.addIngredient(ForgeTags.GRAIN_RICE)
					.addIngredient(ADFood.ARCH_SAUCE)
					.addIngredient(Items.HONEY_BOTTLE)
					.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
					.addIngredient(Items.GLOW_BERRIES)
					.build(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.FOOD,
					ADBlocks.SALAD, 1)::unlockedBy, ADFood.WILDEN_MEAT.asItem())
					.pattern("SAS").pattern("FMF").pattern("CBC")
					.define('C', ForgeTags.SALAD_INGREDIENTS_CABBAGE)
					.define('B', Items.BOWL)
					.define('S', BlockRegistry.SOURCEBERRY_BUSH)
					.define('M', TagGen.COOKED_WILDEN_MEAT)
					.define('F', ItemsRegistry.MAGE_BLOOM)
					.define('A', ADFood.ARCH_SAUCE)
					.save(pvd);

		}

		// fruits
		{

			// jam
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ACTIVATED_MENDOSTEEN_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ACTIVATED_BASTION_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.NEUTRALIZED_FROSTAYA_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);
			}

			// tea
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.MENDOSTEEN_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(BlockRegistry.CASCADING_LEAVE)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BASTION_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(BlockRegistry.FLOURISHING_LEAVES)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BOMBEGRANTE_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(BlockRegistry.VEXING_LEAVES)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.FROSTAYA_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(BlockRegistry.BLAZING_LEAVES)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

			}

			// hornbeer
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.MENDOSTEEN_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BASTION_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BOMBEGRANTE_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.FROSTAYA_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);


			}

			// fruit meat plate
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.MENDOSTEEN_CHICKEN, 1, 200, 0.1f, Items.BOWL)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get())
						.addIngredient(ADFood.ACTIVATED_MENDOSTEEN_JAM.get())
						.addIngredient(ForgeTags.RAW_CHICKEN)
						.addIngredient(ForgeTags.CROPS_CABBAGE)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BASTION_PORK, 1, 200, 0.1f, Items.BOWL)
						.addIngredient(BlockRegistry.BASTION_POD.get())
						.addIngredient(ADFood.ACTIVATED_BASTION_JAM.get())
						.addIngredient(ForgeTags.RAW_PORK)
						.addIngredient(ForgeTags.VEGETABLES_TOMATO)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BOMBEGRANTE_STEAK, 1, 200, 0.1f, Items.BOWL)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get())
						.addIngredient(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM.get())
						.addIngredient(ForgeTags.RAW_BEEF)
						.addIngredient(ForgeTags.VEGETABLES_POTATO)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.FROSTAYA_MUTTON, 1, 200, 0.1f, Items.BOWL)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get())
						.addIngredient(ADFood.NEUTRALIZED_FROSTAYA_JAM.get())
						.addIngredient(ForgeTags.RAW_MUTTON)
						.addIngredient(ForgeTags.VEGETABLES_ONION)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);


			}

		}

	}

	private static void strip(RegistrateRecipeProvider pvd, ItemEntry<?> bark,
							  RegistryWrapper<? extends Block> log,
							  RegistryWrapper<? extends Block> stripped,
							  RegistryWrapper<? extends Block> wood,
							  RegistryWrapper<? extends Block> stwood
	) {
		CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), new ToolActionIngredient(ToolActions.AXE_STRIP), stripped)
				.addResult(bark).addSound(ForgeRegistries.SOUND_EVENTS.getKey(SoundEvents.AXE_STRIP).toString())
				.build(pvd);
		CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(wood), new ToolActionIngredient(ToolActions.AXE_STRIP), stwood)
				.addResult(bark).addSound(ForgeRegistries.SOUND_EVENTS.getKey(SoundEvents.AXE_STRIP).toString())
				.build(pvd);
	}

	private static void meat(RegistrateRecipeProvider pvd, ADFood in, ADFood out, ADFood inslice, ADFood outslice) {
		pvd.smoking(DataIngredient.items(in), RecipeCategory.FOOD, out::asItem, 0.1f);
		pvd.campfire(DataIngredient.items(in), RecipeCategory.FOOD, out::asItem, 0.1f);
		pvd.smoking(DataIngredient.items(inslice), RecipeCategory.FOOD, outslice::asItem, 0.1f);
		pvd.campfire(DataIngredient.items(inslice), RecipeCategory.FOOD, outslice::asItem, 0.1f);
		CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(in), Ingredient.of(ForgeTags.TOOLS_KNIVES),
				inslice, 3).build(pvd);
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}


}
