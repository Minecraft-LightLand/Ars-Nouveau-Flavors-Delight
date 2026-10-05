package dev.xkmc.arsdelight.mixin;

import com.mojang.serialization.Codec;
import dev.xkmc.arsdelight.init.data.ADLootConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Writes table-level {@code neoforge:conditions} for the loot tables registered in
 * {@link ADLootConditions}.
 * <p>
 * {@link LootTableProvider} saves every table with {@code LootTable.DIRECT_CODEC}, which has no
 * {@code neoforge:conditions} field, and Registrate only ever hands it a {@code LootTable.Builder} —
 * so there is no API hook for attaching a condition. This redirects the save call and swaps in
 * {@link ADLootConditions#CODEC} for the tables that need it.
 */
@Mixin(LootTableProvider.class)
public abstract class LootTableProviderMixin {

	@Redirect(method = "lambda$run$4", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/data/DataProvider;saveStable(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/nio/file/Path;)Ljava/util/concurrent/CompletableFuture;",
			remap = false), remap = false)
	private <T> CompletableFuture<?> arsdelight$writeLootTable(CachedOutput output, HolderLookup.Provider registries,
			Codec<T> codec, T value, Path path) {
		if (value instanceof LootTable table) {
			var id = ((LootTableAccessor) (Object) table).arsdelight$getRandomSequence();
			if (id.isPresent()) {
				var conditions = ADLootConditions.get(id.get());
				if (conditions != null)
					return DataProvider.saveStable(output, registries, ADLootConditions.CODEC,
							Optional.of(new WithConditions<>(conditions, table)), path);
			}
		}
		return DataProvider.saveStable(output, registries, codec, value, path);
	}

}