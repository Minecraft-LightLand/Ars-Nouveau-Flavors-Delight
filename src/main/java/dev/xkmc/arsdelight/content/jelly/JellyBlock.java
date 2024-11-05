package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.block.IPrismaticBlock;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import dev.xkmc.l2modularblock.core.DelegateEntityBlockImpl;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.type.BlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class JellyBlock extends DelegateEntityBlockImpl implements IPrismaticBlock {

	private static final BlockMethod INS = new JellyMethod();
	private static final BlockMethod TE = new BlockEntityBlockMethodImpl<>(ADJellys.JELLY_BE, JellyBlockEntity.class);

	public JellyBlock(Properties p) {
		super(p, INS, TE);
	}

	public void onHit(ServerLevel world, BlockPos pos, EntityProjectileSpell spell) {
		if (world.getBlockEntity(pos) instanceof JellyBlockEntity be) {
			spell.spellResolver.spellContext.attachments.put(JellyAttachment.ID, new JellyAttachment(be.getId().toString()));
			be.makeWiggle();
		}
	}

	public void updateEntityAfterFallOn(BlockGetter level, Entity e) {
		if (e.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(level, e);
		} else {
			this.bounceUp(e);
		}
	}

	private void bounceUp(Entity pEntity) {
		Vec3 v = pEntity.getDeltaMovement();
		if (v.y < 0) {
			double r = pEntity instanceof LivingEntity ? 1 : 0.8;
			pEntity.setDeltaMovement(v.x, -v.y * r, v.z);
		}

	}

	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).isSolid();
	}

	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}

}
