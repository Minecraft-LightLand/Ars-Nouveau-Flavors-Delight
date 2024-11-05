package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.block.IPrismaticBlock;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import dev.xkmc.l2modularblock.DelegateEntityBlockImpl;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.type.BlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
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
			var v = spell.getDeltaMovement().normalize();
			be.makeWiggle(Direction.getNearest(v.x, v.y, v.z));
		}
	}

	@Override
	public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile pProjectile) {
		if (!level.isClientSide() && level.getBlockEntity(hit.getBlockPos()) instanceof JellyBlockEntity be) {
			be.makeWiggle(hit.getDirection());
		}
	}

	public void updateEntityAfterFallOn(BlockGetter level, Entity e) {
		if (e.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(level, e);
		} else {
			this.bounceUp(e);
		}
	}

	private void bounceUp(Entity entity) {
		Vec3 v = entity.getDeltaMovement();
		if (v.y < 0) {
			double r = 0.8;
			if (entity instanceof LivingEntity le) {
				r = 1;
				var food = asItem().getFoodProperties();
				if (food != null) {
					for (var e : food.getEffects()) {
						if (e.getSecond() > le.level().getRandom().nextFloat()) {
							var eff = e.getFirst();
							le.addEffect(new MobEffectInstance(eff.getEffect(), eff.getDuration() / 4, eff.getAmplifier() - 1));
						}
					}
				}
			}
			entity.setDeltaMovement(v.x, -v.y * r, v.z);
		}

	}

	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}

}
