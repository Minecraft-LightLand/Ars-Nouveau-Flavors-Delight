package dev.xkmc.arsdelight.content.block;

import dev.xkmc.arsdelight.init.food.ADFood;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.function.Supplier;

public class SaladFeast extends FeastBlock {

	private static final VoxelShape BASE = Block.box(1, 0, 1, 15, 2, 15);
	private static final VoxelShape LOW = Shapes.or(BASE, Block.box(2, 0, 2, 14, 4, 14));
	private static final VoxelShape HIGH = Shapes.or(BASE, Block.box(2, 0, 2, 14, 6, 14));

	public SaladFeast(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		int s = state.getValue(SERVINGS);
		return s == 0 ? BASE : s == 1 ? LOW : HIGH;
	}

	protected ItemInteractionResult takeServing(LevelAccessor level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
		var ans = takeServingImpl(level, pos, state, player, hand, ADFood.HORN_ROLL.asStack(), false);
		if (ans.consumesAction()) return ans;
		return takeServingImpl(level, pos, state, player, hand, ADFood.BOWL_OF_WILDEN_SALAD.asStack(), true);
	}

	protected ItemInteractionResult takeServingImpl(LevelAccessor level, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack serving, boolean fin) {
		int servings = (Integer) state.getValue(this.getServingsProperty());
		if (servings == 0) {
			level.playSound((Player) null, pos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
			level.destroyBlock(pos, true);
			return ItemInteractionResult.SUCCESS;
		} else {
			//ItemStack serving = this.getServingItem(state); REMOVE
			ItemStack heldStack = player.getItemInHand(hand);
			if (servings > 0) {
				if (!serving.hasCraftingRemainingItem() || ItemStack.isSameItem(heldStack, serving.getCraftingRemainingItem())) {
					level.setBlock(pos, (BlockState) state.setValue(this.getServingsProperty(), servings - 1), 3);
					if (!player.getAbilities().instabuild && serving.hasCraftingRemainingItem()) {
						heldStack.shrink(1);
					}

					if (!player.getInventory().add(serving)) {
						player.drop(serving, false);
					}

					if ((Integer) level.getBlockState(pos).getValue(this.getServingsProperty()) == 0 && !this.hasLeftovers) {
						level.removeBlock(pos, false);
					}

					level.playSound((Player) null, pos, SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
					return ItemInteractionResult.SUCCESS;
				}
				if (fin)
					player.displayClientMessage(TextUtils.getTranslation("block.feast.use_container", new Object[]{serving.getCraftingRemainingItem().getHoverName()}), true);
			}

			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
	}

}
