package dev.xkmc.arsdelight.mixin;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.common.block.tile.DrygmyTile;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.arsdelight.events.ArsDelightMixinHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrygmyTile.class)
public abstract class DrygmyTileMixin extends SummoningTile {

	public DrygmyTileMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@WrapOperation(method = "generateItems", remap = false, at = @At(value = "INVOKE", remap = false, target = "Lcom/hollingsworth/arsnouveau/api/ANFakePlayer;getPlayer(Lnet/minecraft/server/level/ServerLevel;)Lcom/hollingsworth/arsnouveau/api/ANFakePlayer;"))
	public ANFakePlayer arsdelight$getFakePlayer$setItem(ServerLevel world, Operation<ANFakePlayer> original) {
		var ans = original.call(world);
		ArsDelightMixinHandler.setFakePlayerItems(world, getBlockPos(), ans);
		return ans;
	}

	@Inject(method = "generateItems", remap = false, at = @At(value = "TAIL", remap = false))
	public void arsdelight$removeFakePlayerItem(CallbackInfo ci) {
		ArsDelightMixinHandler.removeFakePlayerItems(ANFakePlayer.getPlayer((ServerLevel) this.level));
	}

}
