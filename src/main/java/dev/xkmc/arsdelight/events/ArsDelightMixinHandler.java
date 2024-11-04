package dev.xkmc.arsdelight.events;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import dev.xkmc.arsdelight.init.data.ADModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class ArsDelightMixinHandler {

	public static void setFakePlayerItems(ServerLevel level, BlockPos pos, ANFakePlayer player) {
		for (Direction dir : Direction.values()) {
			if (level.getBlockEntity(pos.relative(dir)) instanceof ArcanePedestalTile tile) {
				ItemStack stack = tile.getItem(0);
				if (!stack.isStackable()) {
					ItemStack copy = ADModConfig.COMMON.drygmyFarmingToolPlainCopy.get() ?
							stack.getItem().getDefaultInstance() : stack.copy();
					player.setItemInHand(InteractionHand.MAIN_HAND, copy);
					int cost = ADModConfig.COMMON.drygmyFarmingDamageTool.get();
					if (cost > 0) {
						stack.hurtAndBreak(cost, player, e -> {
						});
					}
					return;
				}
			}
		}
	}

	public static void removeFakePlayerItems(ANFakePlayer player) {
		player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
	}

}
