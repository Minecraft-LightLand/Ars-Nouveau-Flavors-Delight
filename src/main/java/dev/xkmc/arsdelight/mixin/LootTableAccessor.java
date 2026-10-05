package dev.xkmc.arsdelight.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

/**
 * {@link LootTable} keeps {@code randomSequence} private with no getter. {@code LootTableProvider}
 * sets it to the table's own id, so this is how we recover which table is being written.
 */
@Mixin(LootTable.class)
public interface LootTableAccessor {

	@Accessor("randomSequence")
	Optional<ResourceLocation> arsdelight$getRandomSequence();

}