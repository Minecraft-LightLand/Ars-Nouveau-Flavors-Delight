package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.spell.IContextAttachment;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JellyAttachment(String jelly) implements IContextAttachment {

	public static final ResourceLocation ID = ArsDelight.loc("jelly_context");

	@Override
	public ResourceLocation id() {
		return ID;
	}

	public @Nullable PotionContents getData(Level level, SpellContext ctx) {
		var id = ResourceLocation.tryParse(jelly);
		if (id == null) return null;
		Item item = BuiltInRegistries.ITEM.get(id);
		var food = item.getDefaultInstance().getFoodProperties(null);
		if (food == null) return null;
		List<MobEffectInstance> effs = new ArrayList<>();
		for (var e : food.effects()) {
			if (e.probability() > level.getRandom().nextFloat()) {
				var eff = e.effect();
				effs.add(new MobEffectInstance(eff.getEffect(), eff.getDuration() / 4, eff.getAmplifier()));
			}
		}
		if (effs.isEmpty()) return null;
		return new PotionContents(Optional.empty(), Optional.empty(), effs);
	}

}
