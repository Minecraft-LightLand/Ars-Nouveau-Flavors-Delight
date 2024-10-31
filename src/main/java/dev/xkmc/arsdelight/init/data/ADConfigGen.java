package dev.xkmc.arsdelight.init.data;

import dev.xkmc.l2core.serial.config.ConfigDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;

import java.util.concurrent.CompletableFuture;

public class ADConfigGen extends ConfigDataProvider {

	public ADConfigGen(DataGenerator generator, CompletableFuture<HolderLookup.Provider> pvd) {
		super(generator, pvd, "Ars Delight Config");
	}

	@Override
	public void add(Collector collector) {
	}

}
