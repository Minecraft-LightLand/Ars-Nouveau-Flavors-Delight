package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.arsdelight.mixin.AddItemModifierAccessor;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import vectorwing.farmersdelight.common.loot.modifier.AddItemModifier;
import vectorwing.farmersdelight.common.tag.CommonTags;

import java.util.concurrent.CompletableFuture;

public class ADGLMProvider extends GlobalLootModifierProvider {

	public static void register() {
	}

	public ADGLMProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, ArsDelight.MODID);
	}

	@Override
	protected void start() {
		add("scavenge_chimera_meat", create(ADFood.CHIMERA_MEAT.asItem(), 4,
				killTarget(ModEntities.WILDEN_BOSS.get()),
				killedByItem(CommonTags.TOOLS_KNIFE)
		));
		add("scavenge_wilden_hunter_meat", create(ADFood.WILDEN_MEAT.asItem(), 1,
				killTarget(ModEntities.WILDEN_HUNTER.get()),
				killedByItem(CommonTags.TOOLS_KNIFE)
		));
		add("scavenge_chimera_horn", create(ADItems.CHIMERA_HORN.asItem(), 1,
				killTarget(ModEntities.WILDEN_BOSS.get()),
				killedByItem(ItemTags.AXES)
		));
	}

	private static LootItemCondition killTarget(EntityType<?> type) {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.THIS,
				EntityPredicate.Builder.entity().entityType(
						EntityTypePredicate.of(type))).build();
	}

	private static LootItemCondition killedByItem(TagKey<Item> tag) {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.ATTACKER,
				EntityPredicate.Builder.entity().equipment(
						EntityEquipmentPredicate.Builder.equipment().mainhand(
										ItemPredicate.Builder.item().of(tag))
								.build()).build()).build();
	}

	private static AddItemModifier create(Item item, int count, LootItemCondition... conditions) {
		var ans = AddItemModifierAccessor.create(conditions, item, count);
		assert ans != null;
		return ans;
	}

}
