package dev.xkmc.arsdelight.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class ChimeraFeast extends FeastBlock {

	public ChimeraFeast(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return super.getShape(state, level, pos, context);
	}
}
