package dev.xkmc.arsdelight.content.jelly;

import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JellyModel extends GeoModel<JellyBlockEntity> {

	@Override
	public ResourceLocation getModelResource(JellyBlockEntity be) {
		return ArsDelight.loc("geo/jelly.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(JellyBlockEntity be) {
		return be.getId().withPath(e -> "textures/block/jelly/" + e + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(JellyBlockEntity be) {
		return ArsDelight.loc("animations/jelly.animation.json");
	}

}
