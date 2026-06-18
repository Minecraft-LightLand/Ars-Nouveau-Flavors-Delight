package dev.xkmc.arsdelight.compat.archwood;

import alexthw.ars_elemental.registry.ModItems;
import com.alexthw.archwood_good.ArchwoodGood;
import com.alexthw.archwood_good.registry.AWGBlockRegistry;
import com.alexthw.archwood_good.registry.AWGItemRegistry;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.arsdelight.compat.elemental.AERecipeGen;
import dev.xkmc.arsdelight.init.data.RecipeGen;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.l2core.serial.recipe.ConditionalRecipeWrapper;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

public class AWRecipeGen {

	public static void genRecipes(RegistrateRecipeProvider pvd) {

		var out = ConditionalRecipeWrapper.mod(pvd, ArchwoodGood.MODID);

		AERecipeGen.storage(pvd, out, AWGItemRegistry.DAWNBERRY_POD, RecipeCategory.MISC, ArchwoodCompat.DAWNBERRY_CRATE);
		AERecipeGen.storage(pvd, out, AWGItemRegistry.LIGHTCHEE_POD, RecipeCategory.MISC, ArchwoodCompat.LIGHTCHEE_CRATE);


		RecipeGen.pie(pvd, out, ArchwoodCompat.DAWNBERRY_PIE, AWFood.NEUTRALIZED_DAWNBERRY_JAM, AWGItemRegistry.DAWNBERRY_POD.get());
		RecipeGen.pie(pvd, out, ArchwoodCompat.LIGHTCHEE_PIE, AWFood.NEUTRALIZED_LIGHTCHEE_JAM, AWGItemRegistry.LIGHTCHEE_POD.get());

		CookingPotRecipeBuilder.cookingPotRecipe(ArchwoodCompat.DAWNBERRY_JELLY, 1, 200, 0.1f, Items.BOWL)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.DAWNBERRY_POD.get(), 2)
				.addIngredient(ArchwoodCompat.BLEAK_BARK.get())
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.addIngredient(Items.SLIME_BALL)
				.addIngredient(Items.SUGAR)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(ArchwoodCompat.LIGHTCHEE_JELLY, 1, 200, 0.1f, Items.BOWL)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.LIGHTCHEE_POD.get(), 2)
				.addIngredient(ArchwoodCompat.DAWN_BARK.get())
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.addIngredient(Items.SLIME_BALL)
				.addIngredient(Items.SUGAR)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.NEUTRALIZED_DAWNBERRY_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.DAWNBERRY_POD.get(), 2)
				.addIngredient(ArchwoodCompat.BLEAK_BARK.get())
				.addIngredient(Items.SUGAR)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.NEUTRALIZED_LIGHTCHEE_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.LIGHTCHEE_POD.get(), 2)
				.addIngredient(ArchwoodCompat.DAWN_BARK.get())
				.addIngredient(Items.SUGAR)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.DAWNBERRY_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.DAWNBERRY_POD.get(), 2)
				.addIngredient(ArchwoodCompat.BLEAK_BARK.get())
				.addIngredient(AWGBlockRegistry.WHITE_ARCHWOOD_LEAVES)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.LIGHTCHEE_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.LIGHTCHEE_POD.get(), 2)
				.addIngredient(ArchwoodCompat.DAWN_BARK.get())
				.addIngredient(AWGBlockRegistry.ORANGE_ARCHWOOD_LEAVES)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.DAWNBERRY_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.DAWNBERRY_POD.get(), 2)
				.addIngredient(ArchwoodCompat.BLEAK_BARK.get())
				.addIngredient(ItemsRegistry.MAGE_BLOOM)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.LIGHTCHEE_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(AWGItemRegistry.LIGHTCHEE_POD.get(), 2)
				.addIngredient(ArchwoodCompat.DAWN_BARK.get())
				.addIngredient(ItemsRegistry.MAGE_BLOOM)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AWFood.SKITTLE_STEW, 1, 200, 0.1f, ADFood.ARCH_SAUCE)
				.setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
				.addIngredient(AWGItemRegistry.LIGHTCHEE_POD.get())
				.addIngredient(AWGItemRegistry.DAWNBERRY_POD.get())
				.addIngredient(ModItems.FLASHING_POD.get())
				.addIngredient(ADFood.ARCH_SAUCE.get())
				.save(out);

	}

}
