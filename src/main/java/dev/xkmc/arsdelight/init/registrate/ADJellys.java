package dev.xkmc.arsdelight.init.registrate;

import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.arsdelight.compat.diet.DietTagGen;
import dev.xkmc.arsdelight.content.jelly.JellyBlock;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntity;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntityRenderer;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.data.TagGen;
import dev.xkmc.arsdelight.init.food.BlockFoodType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Supplier;

public class ADJellys {

	public static final BlockEntry<JellyBlock> MENDOSTEEN_JELLY, BASTION_JELLY,
			BOMBEGRANTE_JELLY, FROSTAYA_JELLY, SOURCE_BERRY_JELLY;

	public static final BlockEntityEntry<JellyBlockEntity> JELLY_BE;

	static {
		MENDOSTEEN_JELLY = jelly("mendosteen_jelly", () -> resolve(ItemsRegistry.MENDOSTEEN_FOOD, 1, 1));
		BASTION_JELLY = jelly("bastion_jelly", () -> resolve(ItemsRegistry.BASTION_FOOD, 1, 1));
		BOMBEGRANTE_JELLY = jelly("bombegrante_jelly", () -> resolve(ItemsRegistry.BLASTING_FOOD, 1, 1));
		FROSTAYA_JELLY = jelly("frostaya_jelly", () -> resolve(ItemsRegistry.FROSTAYA_FOOD, 1, 1));
		SOURCE_BERRY_JELLY = jelly("source_berry_jelly", () -> resolve(ItemsRegistry.SOURCE_BERRY_FOOD, 2, 4));

		JELLY_BE = ArsDelight.REGISTRATE.blockEntity("jelly", JellyBlockEntity::new)
				.renderer(() -> JellyBlockEntityRenderer::new)
				.validBlocks(MENDOSTEEN_JELLY, BASTION_JELLY, BOMBEGRANTE_JELLY, FROSTAYA_JELLY, SOURCE_BERRY_JELLY)
				.register();
	}

	private static BlockEntry<JellyBlock> jelly(String name, Supplier<FoodProperties.Builder> effs) {
		return ArsDelight.REGISTRATE.block(name, JellyBlock::new)
				.properties((p) -> p.instabreak().pushReaction(PushReaction.DESTROY).mapColor(DyeColor.BROWN).sound(SoundType.WOOL)
						.noOcclusion())
				.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().getBuilder(ctx.getName())
						.parent(new ModelFile.UncheckedModelFile("builtin/entity"))
						.texture("particle", "item/jelly/" + ctx.getName())))
				.item((t, p) -> BlockFoodType.FAST_BOWL.build(t, p, effs.get()))
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/jelly/" + ctx.getName())))
				.tag(DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag, ItemTagProvider.MAGIC_FOOD, TagGen.JELLY).build()
				.lang(ADItems.toEnglishName(name)).register();
	}

	private static FoodProperties.Builder resolve(FoodProperties effs, double dur, int amp) {
		var builder = new FoodProperties.Builder();
		builder.nutrition(4).saturationMod(0.6f);
		for (var e : effs.getEffects()) {
			builder.effect(() -> amplify(e.getFirst(), dur, amp), e.getSecond());
		}
		return builder;
	}

	private static MobEffectInstance amplify(MobEffectInstance ins, double dur, int amp) {
		return new MobEffectInstance(ins.getEffect(), (int) (ins.getDuration() * dur), ins.getAmplifier() + amp);
	}

	public static void register() {
	}

}
