package dev.xkmc.arsdelight.content.jelly;

import dev.xkmc.l2modularblock.mult.FallOnBlockMethod;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import dev.xkmc.l2modularblock.mult.SurviveBlockMethod;
import dev.xkmc.l2modularblock.one.RenderShapeBlockMethod;
import dev.xkmc.l2modularblock.one.ShapeBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public record JellyMethod() implements FallOnBlockMethod, RenderShapeBlockMethod, OnClickBlockMethod, ShapeBlockMethod, SurviveBlockMethod {

	private static final VoxelShape SHAPE = Shapes.or(
			Block.box(3, 0, 3, 13, 1, 13),
			Block.box(4, 1, 4, 12, 10, 12)
	);

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}

	@Override
	public boolean fallOn(Level level, BlockState block, BlockPos pos, Entity entity, float dist) {
		if (entity.isSuppressingBounce()) {
			entity.causeFallDamage(dist, 0.2f, entity.damageSources().fall());
		}
		if (dist > 1 && level.getBlockEntity(pos) instanceof JellyBlockEntity be) {
			be.makeWiggle();
		}
		return false;
	}

	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).isSolid();
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public InteractionResult onClick(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof JellyBlockEntity be) {
			be.makeWiggle();
		}
		return InteractionResult.SUCCESS;
	}

}
