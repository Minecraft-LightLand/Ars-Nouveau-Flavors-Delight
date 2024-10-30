package dev.xkmc.arsdelight.compat.thirst;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThirstCompat {

	public static void init() {
		MinecraftForge.EVENT_BUS.register(ThirstCompat.class);
	}

	@SubscribeEvent
	public static void compat(RegisterThirstValueEvent event) {
	}

}
