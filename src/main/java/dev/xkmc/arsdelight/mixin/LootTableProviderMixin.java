package dev.xkmc.arsdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import dev.xkmc.arsdelight.init.data.ADLootConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Writes table-level {@code neoforge:conditions} for the loot tables registered in
 * {@link ADLootConditions}.
 * <p>
 * {@link LootTableProvider} saves every table with {@code LootTable.DIRECT_CODEC}, which has no
 * {@code neoforge:conditions} field, and Registrate only ever hands it a {@code LootTable.Builder} —
 * so there is no API hook for attaching a condition. This wraps the save call and swaps in
 * {@link ADLootConditions#CODEC} for the tables that need it.
 */
@Mixin(LootTableProvider.class)
public abstract class LootTableProviderMixin {

	// Method target is lambda$run$4, NOT run(...). The saveStable call sits inside the stream lambda,
	// which javac compiles out to a synthetic method. Confirm the real name and signature with:
	//   javap -p build/neoForm/neoFormJoined1.21.1-*/steps/recompile/classes/net/minecraft/data/loot/LootTableProvider.class
	// Spell the descriptor out in full: the bare name only pins the lambda index, so it shifts if a
	// lambda is added to run(). require = 0 keeps such a shift from hard-failing datagen -- but then
	// the mixin is a silent no-op and no table gets its conditions, so after a version bump check that
	// the 9 gated tables in data/arsdelight/loot_table/blocks still start with neoforge:conditions.
	@WrapOperation(method = "lambda$run$4(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;Ljava/util/Map$Entry;)Ljava/util/concurrent/CompletableFuture;",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/data/DataProvider;saveStable(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/nio/file/Path;)Ljava/util/concurrent/CompletableFuture;",
					remap = false),
			remap = false, require = 0)
	private <T> CompletableFuture<?> arsdelight$writeLootTable(CachedOutput output, HolderLookup.Provider registries,
																Codec<T> codec, T value, Path path, Operation<CompletableFuture<?>> original) {
		if (value instanceof LootTable table) {
			var id = ((LootTableAccessor) (Object) table).arsdelight$getRandomSequence();
			if (id.isPresent()) {
				var conditions = ADLootConditions.get(id.get());
				if (conditions != null)
					return original.call(output, registries, ADLootConditions.CODEC,
							Optional.of(new WithConditions<>(conditions, table)), path);
			}
		}
		return original.call(output, registries, codec, value, path);
	}

}