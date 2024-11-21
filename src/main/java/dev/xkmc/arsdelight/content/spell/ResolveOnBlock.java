package dev.xkmc.arsdelight.content.spell;

import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

public abstract class ResolveOnBlock {

	public abstract boolean process(CuttingBoardBlockEntity be, Level level);

}
