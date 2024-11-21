package dev.xkmc.arsdelight.init.registrate;

import com.google.gson.JsonObject;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

public class ADEmptyRecipe {

	public static final RegistryEntry<RecipeType<EmptyRecipe>> RT = ArsDelight.REGISTRATE.recipe("empty");
	public static final RegistryEntry<Serializer> RS = ArsDelight.REGISTRATE.generic("empty",
			Registries.RECIPE_SERIALIZER, Serializer::new).register();

	public static void genEmpty(RegistrateRecipeProvider pvd, String modid, String id) {
		pvd.accept(new FinishedEmptyRecipe(new ResourceLocation(modid, id)));
	}

	public record Serializer() implements RecipeSerializer<EmptyRecipe> {

		@Override
		public EmptyRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
			return new EmptyRecipe(id);
		}

		@Override
		public EmptyRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			return new EmptyRecipe(id);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, EmptyRecipe empty) {

		}
	}

	public record EmptyRecipe(ResourceLocation id) implements Recipe<RecipeWrapper> {

		@Override
		public boolean matches(RecipeWrapper recipeWrapper, Level level) {
			return false;
		}

		@Override
		public ItemStack assemble(RecipeWrapper recipeWrapper, RegistryAccess registryAccess) {
			return ItemStack.EMPTY;
		}

		@Override
		public boolean canCraftInDimensions(int i, int i1) {
			return false;
		}

		@Override
		public ItemStack getResultItem(RegistryAccess registryAccess) {
			return ItemStack.EMPTY;
		}

		@Override
		public ResourceLocation getId() {
			return id;
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

	public record FinishedEmptyRecipe(ResourceLocation id) implements FinishedRecipe {

		@Override
		public void serializeRecipeData(JsonObject json) {
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return RS.get();
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}

	}

	public static void register() {
	}

}
