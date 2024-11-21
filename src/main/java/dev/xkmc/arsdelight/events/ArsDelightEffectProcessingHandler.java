package dev.xkmc.arsdelight.events;

import com.hollingsworth.arsnouveau.api.event.EffectResolveEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBreak;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCrush;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCut;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFell;
import dev.xkmc.arsdelight.content.spell.ResolveCutting;
import dev.xkmc.arsdelight.content.spell.ResolveOnBlock;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@EventBusSubscriber(modid = ArsDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ArsDelightEffectProcessingHandler {

	@SubscribeEvent
	public static void spellResolvePre(EffectResolveEvent.Pre event) {
		if (event.rayTraceResult instanceof BlockHitResult bhit) {
			if (event.world.getBlockEntity(bhit.getBlockPos()) instanceof CuttingBoardBlockEntity be) {
				if (onResolveCutting(be, event.world, event.resolveEffect, event.spellStats)) {
					event.setCanceled(true);
				}
			}
		}
	}

	private static boolean onResolveCutting(CuttingBoardBlockEntity be, Level level, AbstractEffect effect, SpellStats stats) {
		if (stats.getBuffCount(AugmentAOE.INSTANCE) > 0) return false;
		ResolveOnBlock eff = null;
		if (effect instanceof EffectCut) eff = ResolveCutting.cut(stats);
		if (effect instanceof EffectCrush) eff = ResolveCutting.crush(stats);
		if (effect instanceof EffectFell) eff = ResolveCutting.fell(stats);
		if (effect instanceof EffectBreak) eff = ResolveCutting.broke(stats);
		return eff != null && eff.process(be, level);
	}

}
