package dev.xkmc.arsdelight.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

@Mixin(CookingPotRecipeBuilder.class)
public abstract class CookingPotRecipeBuilderMixin {

	@Shadow
	@Final
	private Item result;

	@Shadow
	public abstract void save(RecipeOutput output, ResourceLocation id);

	@Inject(at = @At("HEAD"), method = "build(Lnet/minecraft/data/recipes/RecipeOutput;)V", cancellable = true, remap = false)
	public void arsdelight$respectModid(RecipeOutput consumerIn, CallbackInfo ci) {
		ResourceLocation location = BuiltInRegistries.ITEM.getKey(result);
		save(consumerIn, location.withPrefix("cooking/"));
		ci.cancel();
	}

}
