package dev.xkmc.arsdelight.mixin;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.function.Consumer;

@Mixin(CookingPotRecipeBuilder.class)
public abstract class CookingPotRecipeBuilderMixin {

	@Shadow
	@Final
	private Item result;

	@Shadow
	public abstract void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id);

	@Inject(at = @At("HEAD"), method = "build(Ljava/util/function/Consumer;)V", cancellable = true, remap = false)
	public void arsdelight$respectModid(Consumer<FinishedRecipe> consumerIn, CallbackInfo ci) {
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(result);
		build(consumerIn, location.withPrefix("cooking/"));
		ci.cancel();
	}

}
