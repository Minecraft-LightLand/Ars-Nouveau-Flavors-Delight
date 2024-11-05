package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import dev.xkmc.l2core.base.tile.BaseBlockEntity;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

@SerialClass
public class JellyBlockEntity extends BaseBlockEntity implements GeoBlockEntity {

	private static final RawAnimation WIGGLE = RawAnimation.begin().then("wiggle", Animation.LoopType.PLAY_ONCE);
	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public JellyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public ResourceLocation getId() {
		return getBlockState().getBlock().builtInRegistryHolder().unwrapKey().orElseThrow().location();
	}

	public void makeWiggle() {
		if (level == null) return;
		if (level.isClientSide()) return;
		BlockUtil.updateObservers(level, getBlockPos());
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
			return state.setAndContinue(WIGGLE);
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

}
