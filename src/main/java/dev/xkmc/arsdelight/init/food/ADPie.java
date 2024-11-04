package dev.xkmc.arsdelight.init.food;

import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.compat.diet.DietTagGen;
import dev.xkmc.arsdelight.content.item.ADFoodItem;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.Locale;

public enum ADPie {
	MENDOSTEEN_PIE(true, new EffectEntry(ADEffects.FLOURISH::get, 600)),
	BASTION_PIE(false, new EffectEntry(ADEffects.SHIELDING::get, 600)),
	BOMBEGRANTE_PIE(false, new EffectEntry(ADEffects.BLAST_RES::get, 1200, 1)),
	FROSTAYA_PIE(true, new EffectEntry(ADEffects.FREEZE::get, 2400)),
	;

	public static void register() {

	}

	public final BlockEntry<PieBlock> block;
	public final ItemEntry<ADFoodItem> slice;

	private final boolean deco;

	ADPie(boolean deco, EffectEntry... effects) {
		this.deco = deco;
		String name = name().toLowerCase(Locale.ROOT);
		slice = ArsDelight.REGISTRATE.item(name + "_slice", p -> FoodType.FAST.build(p, 3, 0.3f, effects))
				.tag(DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag, ItemTagProvider.MAGIC_FOOD)
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pie/" + ctx.getName())))
				.defaultLang().register();
		block = ArsDelight.REGISTRATE.block(name,
						p -> new PieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), slice::get))
				.blockstate((ctx, pvd) -> {
					ModelFile[] models = new ModelFile[4];
					for (int i = 0; i < 4; i++) {
						models[i] = genCakeModel(ctx.getName(), pvd, i == 0 ? "" : "_slice" + i);
					}
					pvd.horizontalBlock(ctx.getEntry(), state -> models[state.getValue(PieBlock.BITES)]);
				}).loot((a, b) -> a.dropOther(b, slice)).item()
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pie/" + ctx.getName()))).build()
				.defaultLang().register();
	}

	private BlockModelBuilder genCakeModel(String name, RegistrateBlockstateProvider pvd, String model) {
		String base = name().toLowerCase(Locale.ROOT);
		var id = new ResourceLocation(FarmersDelight.MODID, "block/pie" + model);
		if (deco) {
			id = pvd.modLoc("custom/" + name + model);
		}
		var ans = pvd.models().getBuilder(base + model).parent(new ModelFile.UncheckedModelFile(id))
				.texture("particle", pvd.modLoc("block/pie/" + base + "_top"))
				.texture("top", pvd.modLoc("block/pie/" + base + "_top"))
				.texture("bottom", pvd.modLoc("block/pie/pie_bottom"))
				.texture("side", pvd.modLoc("block/pie/pie_side"))
				.texture("inner", pvd.modLoc("block/pie/" + base + "_inner"));
		if (deco) {
			ans.texture("deco", pvd.modLoc("block/pie/" + base + "_deco"));
		}
		return ans;
	}


}
