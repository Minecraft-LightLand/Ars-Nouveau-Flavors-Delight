package dev.xkmc.arsdelight.init.registrate;

import com.mojang.serialization.MapCodec;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class ADEmptyRecipe {

	public static final Val<RecipeType<EmptyRecipe>> RT =
			SR.of(ArsDelight.REG, BuiltInRegistries.RECIPE_TYPE)
					.reg("empty", RecipeType::simple);
	public static final Val<Serializer> RS =
			SR.of(ArsDelight.REG, BuiltInRegistries.RECIPE_SERIALIZER)
					.reg("empty", Serializer::new);

	public static void genEmpty(RegistrateRecipeProvider pvd, String modid, String id) {
		pvd.accept(ResourceLocation.fromNamespaceAndPath(modid, id), new EmptyRecipe(), null);
	}

	public record Serializer() implements RecipeSerializer<EmptyRecipe> {

		private static final MapCodec<EmptyRecipe> CODEC = MapCodec.unit(EmptyRecipe.INS);
		private static final StreamCodec<RegistryFriendlyByteBuf, EmptyRecipe> STREAM = StreamCodec.unit(EmptyRecipe.INS);

		@Override
		public MapCodec<EmptyRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EmptyRecipe> streamCodec() {
			return STREAM;
		}

	}

	public record EmptyRecipe() implements Recipe<RecipeWrapper> {

		public static final EmptyRecipe INS = new EmptyRecipe();

		@Override
		public boolean matches(RecipeWrapper recipeWrapper, Level level) {
			return false;
		}

		@Override
		public boolean canCraftInDimensions(int i, int i1) {
			return false;
		}

		@Override
		public ItemStack assemble(RecipeWrapper recipeWrapper, HolderLookup.Provider provider) {
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack getResultItem(HolderLookup.Provider provider) {
			return ItemStack.EMPTY;
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return RS.get();
		}

		@Override
		public RecipeType<?> getType() {
			return RT.get();
		}
	}

	public static void register() {
	}

}
