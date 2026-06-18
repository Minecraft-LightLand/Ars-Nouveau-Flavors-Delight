package dev.xkmc.arsdelight.init.food;

import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.arsdelight.compat.diet.DietTagGen;
import dev.xkmc.arsdelight.content.item.ADFoodItem;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

public class ADPie {

	public static final ADPie MENDOSTEEN_PIE = new ADPie("mendosteen_pie", true, new EffectEntry(ADEffects.FLOURISH, 600));
	public static final ADPie BASTION_PIE = new ADPie("bastion_pie", false, new EffectEntry(ADEffects.SHIELDING, 600));
	public static final ADPie BOMBEGRANTE_PIE = new ADPie("bombegrante_pie", false, new EffectEntry(ADEffects.BLAST_RES, 1200, 1));
	public static final ADPie FROSTAYA_PIE = new ADPie("frostaya_pie", true, new EffectEntry(ADEffects.FREEZE, 2400));

	public static void register() {

	}

	public final BlockEntry<PieBlock> block;
	public final ItemEntry<ADFoodItem> slice;

	private final boolean deco;

	public ADPie(String name, boolean deco, EffectEntry... effects) {
		this.deco = deco;
		slice = ArsDelight.REGISTRATE.item(name + "_slice", p -> FoodType.FAST.build(p, 3, 0.3f, effects))
				.asOptional().tag(DietTagGen.FRUITS.tag, DietTagGen.SUGARS.tag, ItemTagProvider.MAGIC_FOOD, ModTags.Items.PIES)
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pie/" + ctx.getName())))
				.lang(ADItems.toEnglishName(name + "_slice")).register();
		block = ArsDelight.REGISTRATE.block(name,
						p -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), slice::get))
				.blockstate((ctx, pvd) -> {
					ModelFile[] models = new ModelFile[4];
					for (int i = 0; i < 4; i++) {
						models[i] = genCakeModel(ctx.getName(), pvd, i == 0 ? "" : "_slice" + i);
					}
					pvd.horizontalBlock(ctx.getEntry(), state -> models[state.getValue(PieBlock.BITES)]);
				}).loot((a, b) -> a.dropOther(b, slice)).item()
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pie/" + ctx.getName()))).build()
				.tag(ModTags.Blocks.PIES)
				.lang(ADItems.toEnglishName(name)).register();
	}

	private BlockModelBuilder genCakeModel(String name, RegistrateBlockstateProvider pvd, String model) {
		var id = pvd.modLoc("custom/pie" + model);
		if (deco) {
			id = pvd.modLoc("custom/" + name + model);
		}
		var ans = pvd.models().getBuilder(name + model).parent(new ModelFile.UncheckedModelFile(id))
				.texture("particle", pvd.modLoc("block/pie/" + name + "_top"))
				.texture("top", pvd.modLoc("block/pie/" + name + "_top"))
				.texture("bottom", pvd.modLoc("block/pie/pie_bottom"))
				.texture("side", pvd.modLoc("block/pie/pie_side"))
				.texture("inner", pvd.modLoc("block/pie/" + name + "_inner"));
		if (deco) {
			ans.texture("deco", pvd.modLoc("block/pie/" + name + "_deco"));
			ans.renderType("cutout");
		}
		return ans;
	}


}
