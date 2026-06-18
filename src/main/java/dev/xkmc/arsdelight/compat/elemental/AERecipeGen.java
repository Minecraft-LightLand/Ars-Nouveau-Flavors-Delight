package dev.xkmc.arsdelight.compat.elemental;

import alexthw.ars_elemental.ArsElemental;
import alexthw.ars_elemental.registry.ModItems;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.arsdelight.init.data.RecipeGen;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.l2core.serial.recipe.ConditionalRecipeWrapper;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

public class AERecipeGen {

	public static void genRecipes(RegistrateRecipeProvider pvd) {

		var out = ConditionalRecipeWrapper.mod(pvd, ArsElemental.MODID);

		storage(pvd, out, ModItems.FLASHING_POD.get(), RecipeCategory.MISC, ElementalCompat.FLASHPINE_CRATE);

		RecipeGen.pie(pvd, out, ElementalCompat.FLASHPINE_PIE, AEFood.NEUTRALIZED_FLASHPINE_JAM, ModItems.FLASHING_POD.get());

		CookingPotRecipeBuilder.cookingPotRecipe(ElementalCompat.FLASHPINE_JELLY, 1, 200, 0.1f, Items.BOWL)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.BLAZING_BARK.get())
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.addIngredient(Items.SLIME_BALL)
				.addIngredient(Items.SUGAR)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AEFood.NEUTRALIZED_FLASHPINE_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.CASCADING_BARK.get())
				.addIngredient(Items.SUGAR)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AEFood.FLASHPINE_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.CASCADING_BARK.get())
				.addIngredient(BlockRegistry.CASCADING_LEAVE)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(out);

		CookingPotRecipeBuilder.cookingPotRecipe(AEFood.FLASHPINE_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
				.setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.CASCADING_BARK.get())
				.addIngredient(ItemsRegistry.MAGE_BLOOM)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(out);

	}

	public static void storage(RegistrateRecipeProvider pvd, RecipeOutput out, ItemLike source, RecipeCategory category, ItemLike output) {
		RecipeGen.unlock(pvd, ShapedRecipeBuilder.shaped(category, output)::unlockedBy, source.asItem())
				.pattern("AAA").pattern("AAA").pattern("AAA")
				.define('A', source)
				.save(out);

		RecipeGen.unlock(pvd, ShapelessRecipeBuilder.shapeless(category, source)::unlockedBy, output.asItem())
				.requires(output)
				.save(out, pvd.safeId(source));
	}
}
