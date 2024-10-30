package dev.xkmc.arsdelight.init.data;

import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;

public class ADConfigGen extends ConfigDataProvider {

	public ADConfigGen(DataGenerator generator) {
		super(generator, "Ars Delight Config");
	}

	@Override
	public void add(Collector collector) {
	}

}
