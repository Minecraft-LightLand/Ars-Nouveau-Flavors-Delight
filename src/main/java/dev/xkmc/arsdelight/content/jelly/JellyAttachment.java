package dev.xkmc.arsdelight.content.jelly;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.api.spell.IContextAttachment;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record JellyAttachment(String jelly) implements IContextAttachment {

	public static final ResourceLocation ID = ArsDelight.loc("jelly_context");

	@Override
	public ResourceLocation id() {
		return ID;
	}

	public @Nullable PotionData getData(Level level, SpellContext ctx) {
		var id = ResourceLocation.tryParse(jelly);
		if (id == null) return null;
		Item item = ForgeRegistries.ITEMS.getValue(id);
		if (item == null) return null;
		var food = item.getFoodProperties();
		if (food == null) return null;
		List<MobEffectInstance> effs = new ArrayList<>();
		for (var e : food.getEffects()) {
			if (e.getSecond() > level.getRandom().nextFloat()) {
				var eff = e.getFirst();
				effs.add(new MobEffectInstance(eff.getEffect(), eff.getDuration() / 4, eff.getAmplifier()));
			}
		}
		if (effs.isEmpty()) return null;
		return new PotionData(Potions.EMPTY, effs, Set.of());
	}

}
