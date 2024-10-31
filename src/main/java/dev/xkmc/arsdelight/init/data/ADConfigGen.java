package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import dev.xkmc.cuisinedelight.content.logic.CookTransformConfig;
import dev.xkmc.cuisinedelight.content.logic.FoodType;
import dev.xkmc.cuisinedelight.content.logic.IngredientConfig;
import dev.xkmc.cuisinedelight.content.logic.transform.Stage;
import dev.xkmc.cuisinedelight.init.CuisineDelight;
import dev.xkmc.l2core.serial.config.ConfigDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ADConfigGen extends ConfigDataProvider {

	public ADConfigGen(DataGenerator generator, CompletableFuture<HolderLookup.Provider> pvd) {
		super(generator, pvd, "Ars Delight Config");
	}

	@Override
	public void add(Collector collector) {
		collector.add(CuisineDelight.INGREDIENT, ArsDelight.loc("meat"), IngredientConfig.build(
				IngredientConfig.get(Ingredient.of(ADFood.WILDEN_MEAT), FoodType.MEAT,
						180, 240, 80, 0.5F, 0.5F, 4, 10,
						new IngredientConfig.EffectEntry(ADEffects.WILDEN, 0, 600)),
				IngredientConfig.get(Ingredient.of(ADFood.WILDEN_MEAT_SLICE), FoodType.MEAT,
						120, 240, 60, 0.5F, 0.5F, 2, 10,
						new IngredientConfig.EffectEntry(ADEffects.WILDEN, 0, 300)),
				IngredientConfig.get(Ingredient.of(ADFood.CHIMERA_MEAT), FoodType.MEAT,
						180, 240, 80, 0.5F, 0.5F, 6, 10,
						new IngredientConfig.EffectEntry(ADEffects.WILDEN, 1, 600)),
				IngredientConfig.get(Ingredient.of(ADFood.CHIMERA_MEAT_SLICE), FoodType.MEAT,
						120, 240, 60, 0.5F, 0.5F, 3, 10,
						new IngredientConfig.EffectEntry(ADEffects.WILDEN, 1, 300)),

				IngredientConfig.get(Ingredient.of(ADFood.WILDEN_SAUCE), FoodType.NONE,
						120, 360, 80, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(ADEffects.WILDEN, 0, 600))
		));

		collector.add(CuisineDelight.INGREDIENT, ArsDelight.loc("fruits"), IngredientConfig.build(
				IngredientConfig.get(Ingredient.of(BlockRegistry.SOURCEBERRY_BUSH), FoodType.VEG,
						120, 240, 60, 0.5F, 0.5F, 1, 10,
						new IngredientConfig.EffectEntry(ModPotions.MANA_REGEN_EFFECT, 0, 600)),
				IngredientConfig.get(Ingredient.of(BlockRegistry.BASTION_POD), FoodType.VEG,
						120, 240, 60, 0.5F, 0.5F, 1, 10,
						new IngredientConfig.EffectEntry(ModPotions.DEFENCE_EFFECT, 0, 1200)),
				IngredientConfig.get(Ingredient.of(BlockRegistry.MENDOSTEEN_POD), FoodType.VEG,
						120, 240, 60, 0.5F, 0.5F, 1, 10,
						new IngredientConfig.EffectEntry(ModPotions.RECOVERY_EFFECT, 0, 1200)),
				IngredientConfig.get(Ingredient.of(BlockRegistry.FROSTAYA_POD), FoodType.VEG,
						120, 240, 60, 0.5F, 0.5F, 1, 10,
						new IngredientConfig.EffectEntry(ModPotions.FREEZING_EFFECT, 0, 600)),
				IngredientConfig.get(Ingredient.of(BlockRegistry.BOMBEGRANTE_POD), FoodType.VEG,
						120, 240, 60, 0.5F, 0.5F, 1, 10,
						new IngredientConfig.EffectEntry(ModPotions.BLAST_EFFECT, 0, 200)),

				IngredientConfig.get(Ingredient.of(ADFood.ACTIVATED_BASTION_JAM), FoodType.NONE,
						120, 360, 80, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(ADEffects.SHIELDING, 0, 600)),
				IngredientConfig.get(Ingredient.of(ADFood.ACTIVATED_MENDOSTEEN_JAM), FoodType.NONE,
						120, 360, 80, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(ADEffects.FLOURISH, 0, 600)),
				IngredientConfig.get(Ingredient.of(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM), FoodType.NONE,
						120, 360, 80, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(ADEffects.BLAST_RES, 0, 1200)),
				IngredientConfig.get(Ingredient.of(ADFood.NEUTRALIZED_FROSTAYA_JAM), FoodType.NONE,
						120, 360, 80, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(ADEffects.FREEZE, 0, 1200)),

				IngredientConfig.get(Ingredient.of(ADFood.ARCH_SAUCE), FoodType.NONE,
						120, 360, 80, 0, 0, 0, 0,
						new IngredientConfig.EffectEntry(ModPotions.MANA_REGEN_EFFECT, 1, 1200))
		));

		collector.add(CuisineDelight.TRANSFORM, ArsDelight.loc("main"), new CookTransformConfig()
				.item(ADFood.WILDEN_MEAT.get(), ADFood.GRILLED_WILDEN_MEAT.get(), Stage.COOKED)
				.item(ADFood.WILDEN_MEAT_SLICE.get(), ADFood.GRILLED_WILDEN_MEAT_SLICE.get(), Stage.COOKED)
				.item(ADFood.CHIMERA_MEAT.get(), ADFood.GRILLED_CHIMERA_MEAT.get(), Stage.COOKED)
				.item(ADFood.CHIMERA_MEAT_SLICE.get(), ADFood.GRILLED_CHIMERA_MEAT_SLICE.get(), Stage.COOKED)
				.fluid(ADFood.ACTIVATED_BASTION_JAM.get(), 0xffffbd0c)
				.fluid(ADFood.ACTIVATED_MENDOSTEEN_JAM.get(), 0xff860639)
				.fluid(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM.get(), 0xffd75525)
				.fluid(ADFood.NEUTRALIZED_FROSTAYA_JAM.get(), 0xff9d8ef4)
				.fluid(ADFood.WILDEN_SAUCE.get(), 0xff8D5944)
				.fluid(ADFood.ARCH_SAUCE.get(), 0xffC89A40)
		);
	}

}
