package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.block.IPrismaticBlock;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@SerialClass
public class JellyBlockEntity extends BaseBlockEntity implements IPrismaticBlock, GeoBlockEntity {

	private static final RawAnimation WIGGLE = RawAnimation.begin().then("wiggle", Animation.LoopType.PLAY_ONCE);
	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public JellyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void onHit(ServerLevel sl, BlockPos pos, EntityProjectileSpell e) {
		Vec3 delta = e.getDeltaMovement();
		Vec3 v;
		if (delta.y() < -0.03) {
			var x = delta.add(1, 0, 1).length();
			if (x < 0.03) {
				var rx = sl.getRandom().nextGaussian();
				var rz = sl.getRandom().nextGaussian();
				var r = new Vec3(rx, 0, rz).normalize().scale(0.03);
				v = delta.multiply(0, -1, 0).add(r);
			} else {
				v = delta.multiply(1, -1, 1);
			}
		} else if (delta.y() < 0.03) {
			v = delta.add(0, 0.06, 0);
		} else {
			v = delta;
		}
		e.setDeltaMovement(v);
		e.setGravity(true);
		e.spellResolver.spellContext.attachments.put(JellyAttachment.ID, new JellyAttachment(getId().toString()));
		makeWiggle();
	}

	public ResourceLocation getId() {
		return getBlockState().getBlock().builtInRegistryHolder().unwrapKey().orElseThrow().location();
	}

	public void makeWiggle() {
		if (level == null) return;
		if (level.isClientSide()) return;
		level.blockEvent(getBlockPos(), getBlockState().getBlock(), 1, 1);
	}

	@Override
	public boolean triggerEvent(int id, int data) {
		if (id == 1 && level != null && level.isClientSide) {
			clientWiggle();
		}
		return id == 1;
	}

	private boolean wiggleOnce = false;

	public void clientWiggle() {
		wiggleOnce = true;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar reg) {
		reg.add(new AnimationController<>(this, "wiggle", 0, this::wiggle));
	}

	private PlayState wiggle(AnimationState<GeoAnimatable> state) {
		if (wiggleOnce) {
			wiggleOnce = false;
			state.getController().forceAnimationReset();
		}
		return state.setAndContinue(WIGGLE);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

}
