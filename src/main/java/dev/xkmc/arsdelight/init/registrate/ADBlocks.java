package dev.xkmc.arsdelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.arsdelight.content.block.ChimeraFeast;
import dev.xkmc.arsdelight.content.block.SaladFeast;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class ADBlocks {

	public static final BlockEntry<Block> MENDOSTEEN_CRATE, BASTION_CRATE,
			BOMBEGRANTE_CRATE, FROSTAYA_CRATE, SOURCE_BERRY_CRATE;
	public static final BlockEntry<CabinetBlock> ARCHWOOD_CABINET;
	public static final BlockEntry<ChimeraFeast> CHIMERA;
	public static final BlockEntry<SaladFeast> SALAD;

	static {

		MENDOSTEEN_CRATE = crate("mendosteen_crate");
		BASTION_CRATE = crate("bastion_crate");
		BOMBEGRANTE_CRATE = crate("bombegrante_crate");
		FROSTAYA_CRATE = crate("frostaya_crate");
		SOURCE_BERRY_CRATE = crate("source_berry_crate");
		ARCHWOOD_CABINET = cabinet("archwood");

		SALAD = ArsDelight.REGISTRATE.block("wilden_salad",
						p -> new SaladFeast(p, ADFood.BOWL_OF_WILDEN_SALAD::get, true))
				.initialProperties(() -> Blocks.CAKE)
				.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.get(), state -> {
					int serve = state.getValue(FeastBlock.SERVINGS);
					String suffix = serve == 0 ? "_leftover" : ("_stage" + (4 - serve));
					return new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + ctx.getName() + suffix));
				})).item().model((ctx, pvd) -> pvd.generated(ctx)).build()
				.loot((pvd, block) -> pvd.add(block, LootTable.lootTable()
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block.asItem())
								.when(ExplosionCondition.survivesExplosion())
								.when(getServe(block))))
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOWL))
								.when(ExplosionCondition.survivesExplosion())
								.when(InvertedLootItemCondition.invert(getServe(block))))
				)).register();

		CHIMERA = ArsDelight.REGISTRATE.block("honey_glazed_chimera",
						p -> new ChimeraFeast(p, ADFood.BOWL_OF_HONEY_GLAZED_CHIMERA::get, true))
				.initialProperties(() -> Blocks.CAKE)
				.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.get(), state -> {
					int serve = state.getValue(FeastBlock.SERVINGS);
					String suffix = serve == 0 ? "_leftover" : ("_stage" + (4 - serve));
					return new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + ctx.getName() + suffix));
				})).item().model((ctx, pvd) -> pvd.generated(ctx)).build()
				.loot((pvd, block) -> pvd.add(block, LootTable.lootTable()
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block.asItem())
								.when(ExplosionCondition.survivesExplosion())
								.when(getServe(block))))
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOWL))
								.when(ExplosionCondition.survivesExplosion())
								.when(InvertedLootItemCondition.invert(getServe(block))))
				)).register();

	}

	private static <T extends FeastBlock> LootItemBlockStatePropertyCondition.Builder getServe(T block) {
		return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).
				setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(block.getServingsProperty(), block.getMaxServings()));
	}

	private static BlockEntry<Block> crate(String name) {
		return ArsDelight.REGISTRATE.block(name, p -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
						.strength(2.0F, 3.0F).sound(SoundType.WOOD)))
				.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().cubeBottomTop(ctx.getName(),
						pvd.modLoc("block/crate/" + name + "_side"),
						pvd.modLoc("block/crate/crate_bottom"),
						pvd.modLoc("block/crate/" + name + "_top"))))
				.tag(BlockTags.MINEABLE_WITH_AXE)
				.simpleItem()
				.lang(ADItems.toEnglishName(name))
				.register();
	}

	private static BlockEntry<CabinetBlock> cabinet(String wood) {
		return ArsDelight.REGISTRATE.block(wood + "_cabinet",
						p -> new CabinetBlock(BlockBehaviour.Properties.copy(Blocks.BARREL))
				).blockstate((ctx, pvd) -> {
					ModelFile close = pvd.models().orientable("block/" + ctx.getName(),
							pvd.modLoc("block/cabinet/" + ctx.getName() + "_side"),
							pvd.modLoc("block/cabinet/" + ctx.getName() + "_front"),
							pvd.modLoc("block/cabinet/" + ctx.getName() + "_top"));
					ModelFile open = pvd.models().orientable("block/" + ctx.getName() + "_open",
							pvd.modLoc("block/cabinet/" + ctx.getName() + "_side"),
							pvd.modLoc("block/cabinet/" + ctx.getName() + "_open"),
							pvd.modLoc("block/cabinet/" + ctx.getName() + "_top"));
					pvd.horizontalBlock(ctx.get(), state -> state.getValue(CabinetBlock.OPEN) ? open : close);
				})
				.tag(BlockTags.MINEABLE_WITH_AXE)
				.simpleItem()
				.register();
	}

	public static void register() {
	}

}
