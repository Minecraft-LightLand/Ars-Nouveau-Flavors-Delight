package dev.xkmc.arsdelight.content.jelly;

import dev.xkmc.l2modularblock.mult.FallOnBlockMethod;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import dev.xkmc.l2modularblock.one.RenderShapeBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public record JellyMethod() implements FallOnBlockMethod, RenderShapeBlockMethod, OnClickBlockMethod {

	@Override
	public boolean fallOn(Level level, BlockState block, BlockPos pos, Entity entity, float dist) {
		if (entity.isSuppressingBounce()) {
			entity.causeFallDamage(dist, 0.2f, entity.damageSources().fall());
		}
		if (level.getBlockEntity(pos) instanceof JellyBlockEntity be) {
			be.makeWiggle();
		}
		return false;
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
