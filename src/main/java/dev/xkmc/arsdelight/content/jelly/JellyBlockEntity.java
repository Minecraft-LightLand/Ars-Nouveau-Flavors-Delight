package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@SerialClass
public class JellyBlockEntity extends BaseBlockEntity implements GeoBlockEntity {

	private static final RawAnimation WIGGLE_SOUTH = RawAnimation.begin().then("wiggle_south", Animation.LoopType.PLAY_ONCE);
	private static final RawAnimation WIGGLE_WEST = RawAnimation.begin().then("wiggle_west", Animation.LoopType.PLAY_ONCE);
	private static final RawAnimation WIGGLE_NORTH = RawAnimation.begin().then("wiggle_north", Animation.LoopType.PLAY_ONCE);
	private static final RawAnimation WIGGLE_EAST = RawAnimation.begin().then("wiggle_east", Animation.LoopType.PLAY_ONCE);
	private static final RawAnimation[] WIGGLES = {WIGGLE_SOUTH, WIGGLE_WEST, WIGGLE_NORTH, WIGGLE_EAST};

	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public JellyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public ResourceLocation getId() {
		return getBlockState().getBlock().builtInRegistryHolder().unwrapKey().orElseThrow().location();
	}

	public void makeWiggle(Direction dir) {
		if (level == null) return;
		if (level.isClientSide()) return;
		BlockUtil.updateObservers(level, getBlockPos());
		int val = dir.getAxis() == Direction.Axis.Y ? level.getRandom().nextInt(4) : dir.get2DDataValue();
		level.blockEvent(getBlockPos(), getBlockState().getBlock(), 1, val);
	}

	@Override
	public boolean triggerEvent(int id, int data) {
		if (id == 1 && level != null && level.isClientSide) {
			wiggleDir = data;
		}
		return id == 1;
	}

	private int wiggleDir = -1;

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar reg) {
		reg.add(new AnimationController<>(this, "wiggle", 0, this::wiggle));
	}

	private PlayState wiggle(AnimationState<GeoAnimatable> state) {
		if (wiggleDir >= 0) {
			var dir = WIGGLES[wiggleDir];
			wiggleDir = -1;
			state.getController().forceAnimationReset();
			return state.setAndContinue(dir);
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

}
