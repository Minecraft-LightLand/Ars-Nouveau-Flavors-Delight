package dev.xkmc.arsdelight.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class ChimeraFeast extends FeastBlock {

	private static final VoxelShape BASE = Block.box(1, 0, 1, 15, 2, 15);
	private static final VoxelShape LOW = Shapes.or(BASE, Block.box(2, 0, 2, 14, 4, 14));
	private static final VoxelShape HIGH = Shapes.or(BASE, Block.box(2, 0, 2, 14, 10, 14));

	public ChimeraFeast(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		int s = state.getValue(SERVINGS);
		return s == 0 ? BASE : s == 1 ? LOW : HIGH;
	}
}
