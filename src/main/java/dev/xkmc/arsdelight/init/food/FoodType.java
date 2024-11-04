package dev.xkmc.arsdelight.init.food;

import dev.xkmc.arsdelight.content.item.ADFoodItem;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum FoodType {
	FAST(true, false, false, null, ADFoodItem::new),
	NORMAL(false, false, false, null, ADFoodItem::new),
	FAST_MEAT(true, false, true, null, ADFoodItem::new),
	MEAT(false, false, true, null, ADFoodItem::new),
	DRINK(true, true, false, () -> Items.GLASS_BOTTLE, ADFoodItem::new),
	HORNED_DRINK(true, true, false, ADItems.CHIMERA_HORN::get, ADFoodItem::new),
	JELLY(false, true, false, () -> Items.GLASS_BOTTLE, ADFoodItem::new),
	BOTTLE(false, false, false, () -> Items.GLASS_BOTTLE, ADFoodItem::new),
	BOWL(false, false, false, () -> Items.BOWL, ADFoodItem::new),
	MEAT_PLATE(false, false, true, () -> Items.BOWL, ADFoodItem::new),
	MEAT_STICK(true, false, true, () -> Items.STICK, ADFoodItem::new),
	HORNED_MEAT(true, false, true, ADItems.CHIMERA_HORN::get, ADFoodItem::new),
	;

	private final boolean fast, always, meat;
	private final @Nullable Supplier<Item> container;
	private final BiFunction<Item.Properties, FoodType, ADFoodItem> factory;

	FoodType(boolean fast, boolean always, boolean meat, @Nullable Supplier<Item> container, BiFunction<Item.Properties, FoodType, ADFoodItem> factory) {
		this.fast = fast;
		this.always = always;
		this.meat = meat;
		this.container = container;
		this.factory = factory;
	}

	ADFoodItem build(Item.Properties prop, FoodProperties.Builder builder) {
		if (fast) builder.fast();
		if (always) builder.alwaysEat();
		if (meat) builder.meat();
		prop.food(builder.build());
		if (container != null) {
			prop.stacksTo(16);
			prop.craftRemainder(container.get());
		}
		return factory.apply(prop, this);
	}

}
