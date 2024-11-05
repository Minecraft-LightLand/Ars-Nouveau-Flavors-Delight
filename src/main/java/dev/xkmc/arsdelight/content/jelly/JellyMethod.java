package dev.xkmc.arsdelight.content.jelly;

import dev.xkmc.l2modularblock.mult.FallOnBlockMethod;
import dev.xkmc.l2modularblock.mult.SurviveBlockMethod;
import dev.xkmc.l2modularblock.mult.UseWithoutItemBlockMethod;
import dev.xkmc.l2modularblock.one.RenderShapeBlockMethod;
import dev.xkmc.l2modularblock.one.ShapeBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

public record JellyMethod() implements FallOnBlockMethod, RenderShapeBlockMethod, UseWithoutItemBlockMethod, ShapeBlockMethod, SurviveBlockMethod {

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
			be.makeWiggle(Direction.UP);
			if (!level.isClientSide() && entity instanceof LivingEntity le) {
				var food = block.getBlock().asItem().getDefaultInstance().getFoodProperties(le);
				if (food != null) {
					for (var e : food.effects()) {
						if (e.probability() > le.level().getRandom().nextFloat()) {
							var eff = e.effect();
							le.addEffect(new MobEffectInstance(eff.getEffect(), eff.getDuration() / 4, eff.getAmplifier() - 1));
						}
					}
				}
			}
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
	public InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof JellyBlockEntity jelly) {
			var dir = hit.getDirection().getOpposite();
			jelly.makeWiggle(dir);
		}
		return InteractionResult.PASS;
	}

}
