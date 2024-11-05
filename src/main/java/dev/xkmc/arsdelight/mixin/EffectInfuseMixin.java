package dev.xkmc.arsdelight.mixin;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInfuse;
import dev.xkmc.arsdelight.content.jelly.JellyAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectInfuse.class)
public class EffectInfuseMixin {

	@Inject(method = "getPotionData", remap = false, cancellable = true, at = @At("HEAD"))
	public void arsdelight$getPotionData(Level level, LivingEntity shooter, SpellContext ctx, CallbackInfoReturnable<PotionContents> cir) {
		JellyAttachment val = ctx.getAttachment(JellyAttachment.ID);
		if (val != null) {
			var data = val.getData(level, ctx);
			if (data != null) {
				cir.setReturnValue(data);
			}
		}
	}

}
