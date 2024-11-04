package dev.xkmc.arsdelight.compat.thirst;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import dev.xkmc.arsdelight.init.food.ADFood;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ThirstCompat {

	public static void init() {
		NeoForge.EVENT_BUS.register(ThirstCompat.class);
	}

	@SubscribeEvent
	public static void compat(RegisterThirstValueEvent event) {
		event.addDrink(ADFood.FROSTAYA_TEA.asItem(), 8, 13);
		event.addDrink(ADFood.BOMBEGRANTE_TEA.asItem(), 8, 13);
		event.addDrink(ADFood.BASTION_TEA.asItem(), 8, 13);
		event.addDrink(ADFood.MENDOSTEEN_TEA.asItem(), 8, 13);
		event.addDrink(ADFood.UNSTABLE_COCKTAIL.asItem(), 8, 13);

		event.addDrink(ADFood.FROSTAYA_HORNBEER.asItem(), 8, 13);
		event.addDrink(ADFood.BOMBEGRANTE_HORNBEER.asItem(), 8, 13);
		event.addDrink(ADFood.BASTION_HORNBEER.asItem(), 8, 13);
		event.addDrink(ADFood.MENDOSTEEN_HORNBEER.asItem(), 8, 13);

		event.addDrink(ADFood.ARCH_SOUP.asItem(), 5, 8);
	}

}
