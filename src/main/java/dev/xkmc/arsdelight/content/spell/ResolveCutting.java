package dev.xkmc.arsdelight.content.spell;

import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModItems;

public class ResolveCutting extends ResolveOnBlock {

	public static ResolveCutting cut(SpellStats stats) {
		if (stats.getBuffCount(AugmentExtract.INSTANCE) > 0)
			return new ResolveCutting(Items.SHEARS);
		return stats.getBuffCount(AugmentAmplify.INSTANCE) > 0 ?
				new ResolveCutting(Items.DIAMOND_AXE, ModItems.DIAMOND_KNIFE.get()) :
				new ResolveCutting(ModItems.DIAMOND_KNIFE.get(), Items.SHEARS);
	}

	public static ResolveOnBlock crush(SpellStats stats) {
		return new ResolveCutting(Items.DIAMOND_SHOVEL);
	}

	public static ResolveOnBlock broke(SpellStats stats) {
		return new ResolveCutting(Items.DIAMOND_PICKAXE);
	}

	public static ResolveOnBlock fell(SpellStats stats) {
		return new ResolveCutting(Items.DIAMOND_AXE);
	}

	private final Item[] items;

	public ResolveCutting(Item... items) {
		this.items = items;
	}

	@Override
	public boolean process(CuttingBoardBlockEntity be, Level level) {
		if (be.isEmpty()) return true;
		ItemStack boardStack = be.getStoredItem().copy();
		for (var e : items) {
			if (be.processStoredItemUsingTool(e.getDefaultInstance(), null)) {
				CuttingBoardBlock.spawnCuttingParticles(level, be.getBlockPos(), boardStack, 5);
				return true;
			}
		}
		return true;
	}

}
