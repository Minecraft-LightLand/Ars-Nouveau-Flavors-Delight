package dev.xkmc.arsdelight.init.registrate;

import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.arsdelight.compat.diet.DietTagGen;
import dev.xkmc.arsdelight.content.jelly.JellyBlock;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntity;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntityRenderer;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.BlockFoodType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class ADJellys {

	public static final BlockEntry<JellyBlock> MENDOSTEEN_JELLY, BASTION_JELLY,
			BOMBEGRANTE_JELLY, FROSTAYA_JELLY;

	public static final BlockEntityEntry<JellyBlockEntity> JELLY_BE;

	static {
		MENDOSTEEN_JELLY = jelly("mendosteen_jelly", ItemsRegistry.MENDOSTEEN_FOOD);
		BASTION_JELLY = jelly("bastion_jelly", ItemsRegistry.BASTION_FOOD);
		BOMBEGRANTE_JELLY = jelly("bombegrante_jelly", ItemsRegistry.BLASTING_FOOD);
		FROSTAYA_JELLY = jelly("frostaya_jelly", ItemsRegistry.FROSTAYA_FOOD);

		JELLY_BE = ArsDelight.REGISTRATE.blockEntity("jelly", JellyBlockEntity::new)
				.renderer(() -> JellyBlockEntityRenderer::new)
				.validBlocks(MENDOSTEEN_JELLY, BASTION_JELLY, BOMBEGRANTE_JELLY, FROSTAYA_JELLY)
				.register();
	}

	private static BlockEntry<JellyBlock> jelly(String name, FoodProperties effs) {
		return ArsDelight.REGISTRATE.block(name, JellyBlock::new)
				.properties((p) -> p.instabreak().pushReaction(PushReaction.DESTROY).mapColor(DyeColor.BROWN).sound(SoundType.WOOL)
						.noOcclusion())
				.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().getBuilder(ctx.getName())
						.parent(new ModelFile.UncheckedModelFile("builtin/entity"))
						.texture("particle", "item/jelly/" + ctx.getName())))
				.item((t, p) -> BlockFoodType.FAST_BOWL.build(t, p, resolve(effs)))
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/jelly/" + ctx.getName())))
				.tag(DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag).build().register();
	}

	private static FoodProperties.Builder resolve(FoodProperties effs) {
		var builder = new FoodProperties.Builder();
		builder.nutrition(4).saturationModifier(0.6f);
		for (var e : effs.effects()) {
			builder.effect(() -> amplify(e.effect()), e.probability());
		}
		return builder;
	}

	private static MobEffectInstance amplify(MobEffectInstance ins) {
		return new MobEffectInstance(ins.getEffect(), ins.getDuration(), ins.getAmplifier() + 1);
	}

	public static void register() {
	}

}
