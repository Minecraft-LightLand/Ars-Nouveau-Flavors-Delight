package dev.xkmc.arsdelight.content.item;

import com.hollingsworth.arsnouveau.api.registry.SpellCasterRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractCaster;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.Color;

// Copied from SwordRenderer
public class KnifeRenderer extends GeoItemRenderer<EnchantersKnife> {
	public KnifeRenderer() {
		super(new GeoModel<>() {
			public ResourceLocation getModelResource(EnchantersKnife wand) {
				return ArsDelight.loc("geo/knife.geo.json");
			}

			public ResourceLocation getTextureResource(EnchantersKnife wand) {
				return ArsDelight.loc("textures/item/enchanters_knife.png");
			}

			public ResourceLocation getAnimationResource(EnchantersKnife wand) {
				return ArsDelight.loc("animations/knife.json");
			}
		});
	}

	public void renderRecursively(PoseStack poseStack, EnchantersKnife animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		if (bone.getName().equals("blade")) {
			super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
		} else {
			super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, Color.WHITE.argbInt());
		}

	}

	public Color getRenderColor(EnchantersKnife animatable, float partialTick, int packedLight) {
		ParticleColor color = ParticleColor.defaultParticleColor();
		AbstractCaster<? extends AbstractCaster<?>> caster = SpellCasterRegistry.from(this.currentItemStack);
		if (caster != null) {
			color = caster.getColor();
		}

		return Color.ofRGBA(color.getRed(), color.getGreen(), color.getBlue(), 0.75F);
	}

	public RenderType getRenderType(EnchantersKnife animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(texture);
	}
}
