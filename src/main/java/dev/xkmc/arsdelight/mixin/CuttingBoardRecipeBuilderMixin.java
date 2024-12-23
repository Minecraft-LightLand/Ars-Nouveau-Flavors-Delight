package dev.xkmc.arsdelight.mixin;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;

@Mixin(CuttingBoardRecipeBuilder.class)
public abstract class CuttingBoardRecipeBuilderMixin {

	@Shadow
	public abstract void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id);

	@Shadow
	@Final
	private Ingredient ingredient;

	@Shadow
	@Final
	private List<ChanceResult> results;

	@Inject(at = @At("HEAD"), method = "build(Ljava/util/function/Consumer;)V", cancellable = true, remap = false)
	public void arsdelight$respectModid(Consumer<FinishedRecipe> consumerIn, CallbackInfo ci) {
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(ingredient.getItems()[0].getItem());
		ResourceLocation res = ForgeRegistries.ITEMS.getKey(results.get(results.size() - 1).getStack().getItem());
		build(consumerIn, res.withPath("cutting/" + location.getPath()));
		ci.cancel();
	}

}
