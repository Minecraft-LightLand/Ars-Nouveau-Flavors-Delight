package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.RecipeDatagen;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import vectorwing.farmersdelight.common.registry.ModItems;

public class ADApparatusRecipeGen extends ApparatusRecipeProvider {

	public ADApparatusRecipeGen(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	public void addEntries() {

		addRecipe(builder().withResult(ADItems.KNIFE)
				.withReagent(ModItems.DIAMOND_KNIFE)
				.withPedestalItem(1, Ingredient.of(Tags.Items.GEMS_DIAMOND))
				.withPedestalItem(1, Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD))
				.withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
				.keepNbtOfReagent(true).build());

	}
}
