package dev.xkmc.arsdelight.content.jelly;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class JellyBlockEntityRenderer extends GeoBlockRenderer<JellyBlockEntity> {

	public JellyBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		super(new JellyModel());
	}

}
