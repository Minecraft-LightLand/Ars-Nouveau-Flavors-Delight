package dev.xkmc.arsdelight.content.item;

import com.hollingsworth.arsnouveau.common.items.ModBlockItem;
import dev.xkmc.arsdelight.init.food.BlockFoodType;
import dev.xkmc.arsdelight.init.food.FoodType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

public class ADFoodBlockItem extends ModBlockItem {

	private final BlockFoodType type;

	public ADFoodBlockItem(Block block, Properties props, BlockFoodType type) {
		super(block, props);
		this.type = type;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.EAT;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		if (Configuration.FOOD_EFFECT_TOOLTIP.get())
			ADFoodItem.getFoodEffects(stack, list);
	}

}
