package dev.xkmc.arsdelight.compat.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.resources.ResourceLocation;

public class CreateRecipeGen {

	public static void onRecipeGen(RegistrateRecipeProvider pvd) {

	}


	private static ProcessingRecipeBuilder<MixingRecipe> mixing(ResourceLocation id) {
		ProcessingRecipeSerializer<MixingRecipe> ser = AllRecipeTypes.MIXING.getSerializer();
		return new ProcessingRecipeBuilder<>(ser.getFactory(), id);
	}

	private static ProcessingRecipeBuilder<CompactingRecipe> compacting(ResourceLocation id) {
		ProcessingRecipeSerializer<CompactingRecipe> ser = AllRecipeTypes.COMPACTING.getSerializer();
		return new ProcessingRecipeBuilder<>(ser.getFactory(), id);
	}

	private static ProcessingRecipeBuilder<FillingRecipe> filling(ResourceLocation id) {
		ProcessingRecipeSerializer<FillingRecipe> ser = AllRecipeTypes.FILLING.getSerializer();
		return new ProcessingRecipeBuilder<>(ser.getFactory(), id);
	}

	private static ProcessingRecipeBuilder<EmptyingRecipe> emptying(ResourceLocation id) {
		ProcessingRecipeSerializer<EmptyingRecipe> ser = AllRecipeTypes.EMPTYING.getSerializer();
		return new ProcessingRecipeBuilder<>(ser.getFactory(), id);
	}

	private static ProcessingRecipeBuilder<CuttingRecipe> cutting(ResourceLocation id) {
		ProcessingRecipeSerializer<CuttingRecipe> ser = AllRecipeTypes.CUTTING.getSerializer();
		return new ProcessingRecipeBuilder<>(ser.getFactory(), id);
	}


}
