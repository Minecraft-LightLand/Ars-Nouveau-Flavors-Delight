package dev.xkmc.arsdelight.init.data;

import com.mojang.serialization.Codec;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Loot tables that must only load when certain optional mods are present.
 * <p>
 * A pool-level {@code neoforge:conditions} cannot do this: the condition lives inside the table, so
 * the body is parsed first, and a missing item makes {@code LootItem}'s codec hard-fail and drop the
 * whole table before any condition is reached. The condition must sit at the table level, where
 * {@link ConditionalOps} evaluates it <em>before</em> decoding the body. Registrate cannot emit that,
 * so {@code LootTableProviderMixin} writes it on our behalf.
 */
public class ADLootConditions {

	/**
	 * Encoding wrapper for a table-level condition. Note this must be
	 * {@code createConditionalCodecWithConditions}, not {@code createConditionalCodec}: the latter
	 * re-wraps on the encode side with an empty condition list, which would silently drop them.
	 */
	public static final Codec<Optional<WithConditions<LootTable>>> CODEC =
			ConditionalOps.createConditionalCodecWithConditions(LootTable.DIRECT_CODEC);

	private static final Map<ResourceLocation, List<ICondition>> CONDITIONS = new LinkedHashMap<>();

	/**
	 * Gate the block loot tables {@code arsdelight:blocks/<name>} for each given block name, requiring
	 * every listed mod. Call this from the compat class that creates the blocks — the blocks only exist
	 * when the mod is loaded, so their loot tables would otherwise reference unresolvable items.
	 */
	public static void requireMods(String modid, String... blockNames) {
		List<ICondition> conditions = List.of(new ModLoadedCondition(modid));
		for (var name : blockNames)
			CONDITIONS.put(ArsDelight.loc("blocks/" + name), conditions);
	}

	/**
	 * Conditions for the given table id, or null when it needs no gating.
	 */
	public static List<ICondition> get(ResourceLocation id) {
		return CONDITIONS.get(id);
	}

}