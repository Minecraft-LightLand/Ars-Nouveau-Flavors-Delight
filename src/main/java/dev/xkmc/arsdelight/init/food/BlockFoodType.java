package dev.xkmc.arsdelight.init.food;

import dev.xkmc.arsdelight.content.item.ADFoodBlockItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum BlockFoodType {
	FAST(true, false, false, null, ADFoodBlockItem::new),
	FAST_BOWL(true, false, false, () -> Items.BOWL, ADFoodBlockItem::new),
	;

	interface Factory {

		ADFoodBlockItem create(Block block, Item.Properties prop, BlockFoodType type);

	}

	private final boolean fast, always, meat;
	private final @Nullable Supplier<Item> container;
	private final Factory factory;

	BlockFoodType(boolean fast, boolean always, boolean meat, @Nullable Supplier<Item> container, Factory factory) {
		this.fast = fast;
		this.always = always;
		this.meat = meat;
		this.container = container;
		this.factory = factory;
	}

	public ADFoodBlockItem build(Block block, Item.Properties prop, FoodProperties.Builder builder) {
		if (fast) builder.fast();
		if (always) builder.alwaysEdible();
		prop.food(builder.build());
		if (container != null) {
			prop.stacksTo(16);
			prop.craftRemainder(container.get());
		}
		return factory.create(block, prop, this);
	}

}
