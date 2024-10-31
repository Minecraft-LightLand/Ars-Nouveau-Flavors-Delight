package dev.xkmc.arsdelight.init;

import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.ghen.thirst.Thirst;
import dev.xkmc.arsdelight.compat.thirst.ThirstCompat;
import dev.xkmc.arsdelight.init.data.*;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADBlocks;
import dev.xkmc.arsdelight.init.registrate.ADEffects;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.PacketHandler;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(ArsDelight.MODID)
@Mod.EventBusSubscriber(modid = ArsDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArsDelight {

	public static final String MODID = "arsdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public static final PacketHandler HANDLER = new PacketHandler(new ResourceLocation(MODID, "main"), 1
	);

	public static final RegistryEntry<CreativeModeTab> TAB =
			REGISTRATE.buildModCreativeTab("arsdelight", "Ars Nouveau's Flavors & Delight",
					e -> e.icon(ADFood.SOURCE_BERRY_PIE_SLICE::asStack));

	public ArsDelight() {
		ADItems.register();
		ADEffects.register();
		ADModConfig.init();
		ADGLMProvider.register();

		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipes);
		REGISTRATE.addDataGenerator(ProviderType.LANG, ADLangData::genLang);
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ADFood.CHIMERA_MEAT.get().withTooltip(ADLangData.KNIFE_KILL.get(ModEntities.WILDEN_BOSS.get().getDescription()));
			ADFood.WILDEN_MEAT.get().withTooltip(ADLangData.KNIFE_KILL.get(ModEntities.WILDEN_HUNTER.get().getDescription()));
			ADItems.CHIMERA_HORN.get().withTooltip(ADLangData.AXE_KILL.get(ModEntities.WILDEN_BOSS.get().getDescription()));
			if (ADModConfig.COMMON.enableThirstCompat.get() && ModList.get().isLoaded(Thirst.ID))
				ThirstCompat.init();

		});
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean server = event.includeServer();
		var gen = event.getGenerator();
		PackOutput output = gen.getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		gen.addProvider(server, new ADConfigGen(gen));
		gen.addProvider(server, new ADGLMProvider(output));
	}

	public static ResourceLocation loc(String id) {
		return new ResourceLocation(MODID, id);
	}

}
