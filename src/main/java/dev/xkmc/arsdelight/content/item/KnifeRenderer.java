package dev.xkmc.arsdelight.content.item;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.renderer.item.FixedGeoItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.model.GeoModel;

// Copied from SwordRenderer
public class KnifeRenderer extends FixedGeoItemRenderer<EnchantersKnife> {
	public KnifeRenderer() {
		super(new GeoModel<EnchantersKnife>() {
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

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext type, PoseStack pose, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		pose.pushPose();
		if (type == ItemDisplayContext.FIXED) {
			pose.translate(0, 0.8, 0.5);
			pose.mulPose(Axis.XP.rotationDegrees(-45));
			pose.translate(0, -0.8, -0.5);
		}
		super.renderByItem(stack, type, pose, bufferSource, packedLight, packedOverlay);
		pose.popPose();
	}

	public void renderRecursively(PoseStack poseStack, EnchantersKnife animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("blade")) {
			super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
		} else {
			super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, (float) Color.WHITE.getRed() / 255.0F, (float) Color.WHITE.getGreen() / 255.0F, (float) Color.WHITE.getBlue() / 255.0F, (float) Color.WHITE.getAlpha() / 255.0F);
		}

	}

	public Color getRenderColor(EnchantersKnife animatable, float partialTick, int packedLight) {
		ParticleColor color = ParticleColor.defaultParticleColor();
		if (this.currentItemStack.hasTag() && this.currentItemStack.getOrCreateTag().contains("ars_nouveau:caster")) {
			color = animatable.getSpellCaster(this.currentItemStack).getColor();
		}

		return Color.ofRGBA(color.getRed(), color.getGreen(), color.getBlue(), 0.75F);
	}

	public RenderType getRenderType(EnchantersKnife animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(texture);
	}

}
