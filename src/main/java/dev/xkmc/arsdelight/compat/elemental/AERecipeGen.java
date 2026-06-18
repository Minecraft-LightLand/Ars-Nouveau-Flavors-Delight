package dev.xkmc.arsdelight.compat.elemental;

import alexthw.ars_elemental.registry.ModItems;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.arsdelight.init.data.RecipeGen;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

public class AERecipeGen {

	public static void genRecipes(RegistrateRecipeProvider pvd) {

		pvd.storage(ModItems.FLASHING_POD::get, RecipeCategory.MISC, ElementalCompat.FLASHPINE_CRATE);
		RecipeGen.pie(pvd, ElementalCompat.FLASHPINE_PIE, AEFood.NEUTRALIZED_FLASHPINE_JAM, ModItems.FLASHING_POD.get());

		CookingPotRecipeBuilder.cookingPotRecipe(ElementalCompat.FLASHPINE_JELLY, 1, 200, 0.1f, Items.BOWL)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.BLAZING_BARK.get())
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.addIngredient(Items.SLIME_BALL)
				.addIngredient(Items.SUGAR)
				.save(pvd);

		CookingPotRecipeBuilder.cookingPotRecipe(AEFood.NEUTRALIZED_FLASHPINE_JAM, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.CASCADING_BARK.get())
				.addIngredient(Items.SUGAR)
				.save(pvd);

		CookingPotRecipeBuilder.cookingPotRecipe(AEFood.FLASHPINE_TEA, 1, 200, 0.1f, Items.GLASS_BOTTLE)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.CASCADING_BARK.get())
				.addIngredient(BlockRegistry.CASCADING_LEAVE)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(pvd);

		CookingPotRecipeBuilder.cookingPotRecipe(AEFood.FLASHPINE_HORNBEER, 1, 200, 0.1f, ADItems.CHIMERA_HORN)
				.addIngredient(ModItems.FLASHING_POD.get(), 2)
				.addIngredient(ADItems.CASCADING_BARK.get())
				.addIngredient(ItemsRegistry.MAGE_BLOOM)
				.addIngredient(BlockRegistry.SOURCEBERRY_BUSH)
				.save(pvd);

	}
}
