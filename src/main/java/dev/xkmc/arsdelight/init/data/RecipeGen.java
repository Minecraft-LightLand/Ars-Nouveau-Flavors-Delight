package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.registry.RegistryWrapper;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
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
							Ingredient.of(ItemTags.SHOVELS),
							ADItems.HORN_POWDER, 1)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(ItemsRegistry.WILDEN_SPIKE),
							Ingredient.of(ItemTags.SHOVELS),
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
							Ingredient.of(CommonTags.TOOLS_KNIFE),
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
					.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
					.addIngredient(ADFood.ARCH_SAUCE)
					.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
					.addIngredient(Tags.Items.EGGS)
					.addIngredient(Items.WHEAT)
					.addIngredient(CommonTags.FOODS_MILK)
					.build(pvd);
		}

		// dish
		{
			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ARCH_SAUCE, 1, 200, 0.1f, Items.BOWL)
					.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
					.addIngredient(ADFood.NEUTRALIZED_FROSTAYA_JAM)
					.addIngredient(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM)
					.addIngredient(ADFood.ACTIVATED_BASTION_JAM)
					.addIngredient(ADFood.ACTIVATED_MENDOSTEEN_JAM)
					.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
					.build(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.WILDEN_SAUCE, 1, 200, 0.1f, Items.BOWL)
					.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
					.addIngredient(ADItems.HORN_POWDER)
					.addIngredient(ADItems.SPIKE_POWDER)
					.addIngredient(ItemsRegistry.WILDEN_WING)
					.build(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ARCH_SOUP, 1, 200, 0.1f, Items.BOWL)
					.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
					.addIngredient(ADItems.BLAZING_BARK)
					.addIngredient(ADItems.CASCADING_BARK)
					.addIngredient(ADItems.FLOURISHING_BARK)
					.addIngredient(ADItems.VEXING_BARK)
					.build(pvd);

			/* TODO more wilden food
			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.WILDEN_STEW, 1, 200, 0.1f, Items.BOWL)
					.addIngredient(TagGen.RAW_WILDEN_MEAT)
					.addIngredient(ForgeTags.VEGETABLES_TOMATO)
					.addIngredient(ForgeTags.VEGETABLES_ONION)
					.addIngredient(ForgeTags.SALAD_INGREDIENTS_CABBAGE)
					.addIngredient(ADItems.HORN_POWDER)
					.build(pvd);

			CookingPotRecipeBuilder.cookingPotRecipe(ADFood.WILDEN_SKEWER, 2, 200, 0.1f, Items.STICK)
					.addIngredient(TagGen.RAW_WILDEN_MEAT)
					.addIngredient(ForgeTags.VEGETABLES_TOMATO)
					.addIngredient(ForgeTags.VEGETABLES_ONION)
					.addIngredient(ADItems.SPIKE_POWDER)
					.build(pvd);
			*/

			CookingPotRecipeBuilder.cookingPotRecipe(ADBlocks.CHIMERA, 1, 200, 0.1f, Items.BOWL)
					.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
					.addIngredient(ADFood.CHIMERA_MEAT)
					.addIngredient(ModItems.RICE.get())
					.addIngredient(ADFood.ARCH_SAUCE)
					.addIngredient(Items.HONEY_BOTTLE)
					.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
					.addIngredient(Items.GLOW_BERRIES)
					.build(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.FOOD,
					ADBlocks.SALAD, 1)::unlockedBy, ADFood.WILDEN_MEAT.asItem())
					.pattern("SAS").pattern("FMF").pattern("CBC")
					.define('C', CommonTags.FOODS_CABBAGE)
					.define('B', Items.BOWL)
					.define('S', BlockRegistry.SOURCEBERRY_BUSH)
					.define('M', ADFood.GRILLED_WILDEN_MEAT)
					.define('F', ItemsRegistry.MAGE_BLOOM)
					.define('A', ADFood.ARCH_SAUCE)
					.save(pvd);

		}

		// fruits
		{

			// jelly
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADJellys.MENDOSTEEN_JELLY, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MISC)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.addIngredient(Items.SLIME_BALL)
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADJellys.BASTION_JELLY, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MISC)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.addIngredient(Items.SLIME_BALL)
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADJellys.BOMBEGRANTE_JELLY, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MISC)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.addIngredient(Items.SLIME_BALL)
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADJellys.FROSTAYA_JELLY, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MISC)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.addIngredient(Items.SLIME_BALL)
						.addIngredient(Items.SUGAR)
						.build(pvd);
			}

			// jam
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ACTIVATED_MENDOSTEEN_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.ACTIVATED_BASTION_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.NEUTRALIZED_FROSTAYA_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(Items.SUGAR)
						.build(pvd);
			}

			// tea
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.MENDOSTEEN_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(BlockRegistry.CASCADING_LEAVE)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BASTION_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(BlockRegistry.FLOURISHING_LEAVES)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BOMBEGRANTE_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(BlockRegistry.VEXING_LEAVES)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.FROSTAYA_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(BlockRegistry.BLAZING_LEAVES)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.UNSTABLE_COCKTAIL, 1, 200, 0.1f, Items.GLASS_BOTTLE)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(Items.GUNPOWDER)
						.addIngredient(Items.SUGAR)
						.addIngredient(ItemsRegistry.FIRE_ESSENCE)
						.build(pvd);

			}

			// hornbeer
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.MENDOSTEEN_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get(), 2)
						.addIngredient(ADItems.CASCADING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BASTION_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BASTION_POD.get(), 2)
						.addIngredient(ADItems.FLOURISHING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BOMBEGRANTE_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get(), 2)
						.addIngredient(ADItems.VEXING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.FROSTAYA_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
						.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get(), 2)
						.addIngredient(ADItems.BLAZING_BARK.get())
						.addIngredient(ItemsRegistry.MAGE_BLOOM)
						.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
						.build(pvd);


			}

			// fruit meat plate
			{
				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.MENDOSTEEN_CHICKEN, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
						.addIngredient(BlockRegistry.MENDOSTEEN_POD.get())
						.addIngredient(ADFood.ACTIVATED_MENDOSTEEN_JAM.get())
						.addIngredient(CommonTags.FOODS_RAW_CHICKEN)
						.addIngredient(CommonTags.FOODS_CABBAGE)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BASTION_PORK, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
						.addIngredient(BlockRegistry.BASTION_POD.get())
						.addIngredient(ADFood.ACTIVATED_BASTION_JAM.get())
						.addIngredient(CommonTags.FOODS_RAW_PORK)
						.addIngredient(CommonTags.FOODS_TOMATO)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.BOMBEGRANTE_STEAK, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
						.addIngredient(BlockRegistry.BOMBEGRANTE_POD.get())
						.addIngredient(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM.get())
						.addIngredient(CommonTags.FOODS_RAW_BEEF)
						.addIngredient(Items.POTATO)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);

				CookingPotRecipeBuilder.cookingPotRecipe(ADFood.FROSTAYA_MUTTON, 1, 200, 0.1f, Items.BOWL)
						.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
						.addIngredient(BlockRegistry.FROSTAYA_POD.get())
						.addIngredient(ADFood.NEUTRALIZED_FROSTAYA_JAM.get())
						.addIngredient(CommonTags.FOODS_RAW_MUTTON)
						.addIngredient(CommonTags.FOODS_ONION)
						.addIngredient(ADFood.WILDEN_SAUCE)
						.build(pvd);


			}

		}

	}

	private static void strip(RegistrateRecipeProvider pvd, ItemEntry<?> bark,
							  RegistryWrapper<Block, ?> log,
							  RegistryWrapper<Block, ?> stripped,
							  RegistryWrapper<Block, ?> wood,
							  RegistryWrapper<Block, ?> stwood
	) {
		CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), new ItemAbilityIngredient(ItemAbilities.AXE_STRIP).toVanilla(), stripped)
				.addResult(bark).addSound(SoundEvents.AXE_STRIP)
				.build(pvd);
		CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(wood), new ItemAbilityIngredient(ItemAbilities.AXE_STRIP).toVanilla(), stwood)
				.addResult(bark).addSound(SoundEvents.AXE_STRIP)
				.build(pvd);
	}

	private static void meat(RegistrateRecipeProvider pvd, ADFood in, ADFood out, ADFood inslice, ADFood outslice) {
		pvd.smoking(DataIngredient.items(in), RecipeCategory.FOOD, out::asItem, 0.1f);
		pvd.campfire(DataIngredient.items(in), RecipeCategory.FOOD, out::asItem, 0.1f);
		pvd.smoking(DataIngredient.items(inslice), RecipeCategory.FOOD, outslice::asItem, 0.1f);
		pvd.campfire(DataIngredient.items(inslice), RecipeCategory.FOOD, outslice::asItem, 0.1f);
		CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(in), Ingredient.of(CommonTags.TOOLS_KNIFE),
				inslice, 3).build(pvd);
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, Criterion<InventoryChangeTrigger.TriggerInstance>, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCriterion(pvd));
	}


}
