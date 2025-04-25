package dev.xkmc.arsdelight.init.data;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.documentation.DocCategory;
import com.hollingsworth.arsnouveau.api.documentation.builder.DocEntryBuilder;
import com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry;
import com.hollingsworth.arsnouveau.setup.registry.Documentation;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.arsdelight.init.food.ADFood;
import dev.xkmc.arsdelight.init.registrate.ADItems;
import dev.xkmc.arsdelight.init.registrate.ADJellys;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class ADDoc {

	public static DocCategory CATEGORY;

	public static void init() {
		CATEGORY = new DocCategory(ArsDelight.loc("category_title"),
				ADFood.SOURCE_BERRY_PIE_SLICE.asStack(), 1943);
		DocumentationRegistry.registerMainCategory(CATEGORY);
	}

	public static void addPages() {
		Documentation.addPage(of("drygmy_weapon")
						.withIcon(ModItems.IRON_KNIFE.get())
						.withLocalizedText())
				.withRelation(ArsNouveau.prefix("drygmy_charm"))
				.withRelation(ArsNouveau.prefix("mob_jar"));

		Documentation.addPage(of(ADItems.KNIFE)
				.withIntroPage()
				.withCraftingPages());

		Documentation.addPage(of("effects")
				.withIcon(ADFood.NEUTRALIZED_BOMBEGRANTE_JAM)
				.withIntroPage()
				.withLocalizedText(ADFood.MENDOSTEEN_TEA)
				.withLocalizedText(ADFood.BASTION_TEA)
				.withLocalizedText(ADFood.BOMBEGRANTE_TEA)
				.withLocalizedText(ADFood.FROSTAYA_TEA)
				.withLocalizedText(ADFood.WILDEN_MEAT));

		Documentation.addPage(of("jelly")
						.withIcon(ADJellys.MENDOSTEEN_JELLY)
						.withIntroPage())
				.withRelation(ArsNouveau.prefix("glyph_infuse"))
				.withRelation(ArsNouveau.prefix("block.ars_nouveau.spell_prism"))
				.withRelation(ArsNouveau.prefix("basic_spell_turret"));

		Documentation.addPage(of("resources")
				.withIcon(ADItems.CHIMERA_HORN)
				.withIntroPage()
				.withLocalizedText(ADItems.BLAZING_BARK)
				.withLocalizedText(ADFood.WILDEN_MEAT)
				.withLocalizedText(ADFood.CHIMERA_MEAT)
				.withLocalizedText(ADItems.CHIMERA_HORN));

		Documentation.addPage(of("spell_automation")
						.withIcon(ModBlocks.CUTTING_BOARD.get())
						.withIntroPage())
				.withRelation(ArsNouveau.prefix("glyph_break"))
				.withRelation(ArsNouveau.prefix("glyph_cut"))
				.withRelation(ArsNouveau.prefix("glyph_fell"))
				.withRelation(ArsNouveau.prefix("glyph_crush"));
	}


	public static void addLang(RegistrateLangProvider pvd) {
		pvd.add("arsdelight.section.category_title", "Ars Delight - Mechanic");

		pvd.add("arsdelight.page.drygmy_weapon", "Drygmy using Weapon");
		pvd.add("arsdelight.page1.drygmy_weapon",
				"With Ars Delight, Drygmy can simulate entity loot with tools on an Arcane Pedestal right next to Drygmy Henge.\n\n" +
						"This feature allows Drygmy to farm scavenging loot from Farmer's Delight and its addons.");

		pvd.add("arsdelight.page1.enchanters_knife",
				"Applies a Touch spell before damaging an entity. Apply a spell at the Scribes Table that does NOT contain a form, such as Ignite -> Extend Time.\n\n" +
						"Unlike Enchanter's Sword, it does not provide additional Amplify augment.");

		pvd.add("arsdelight.page.jelly", "Archwood Pod Jelly");
		pvd.add("arsdelight.page1.jelly",
				"Archwood pod jellies are made from archwood fruits and barks, with enhanced fruit effect. Jelly on ground bounces up falling entities and applies jelly effect.\n\n" +
						"Spell projectiles can pass through jelly blocks. If the spell has infuse effect, override the potion effect, then it won't consume potions.");

		pvd.add("arsdelight.page.spell_automation", "Spell Automation");
		pvd.add("arsdelight.page1.spell_automation", "When spells hit a cutting board, it would simulate tools processing items on the cutting board based on the spell effect:" +
				"\n- Cut: Knife or Shear" +
				"\n- Cut + Amplify: Axe or Knife" +
				"\n- Cut + Extract: Shear only" +
				"\n- Break: Pickaxe" +
				"\n- Crush: Shovel" +
				"\n- Fell: Axe" +
				"\nHaving AOE augment will cancel cutting board automation and proceed with original spell effect.");

		// effects
		pvd.add("arsdelight.page.effects", "Food Effects");
		pvd.add("arsdelight.page1.effects",
				"By cooking fruits from one archwood with barks from another, one can neutralize the negative effects into something new and activate more beneficial effects.\n\n" +
						"Food made with wilden drops will provide wilden effect that aids spell casting. Food with better ingredient gives higher level.");
		pvd.add("arsdelight.page2.effects", "Flourishing effect will restore player mana when player heals. " +
				"When player recovers x%% of their health, this effect will recover same percentage of player mana.\n\n" +
				"Every level up doubles mana restoration");
		pvd.add("arsdelight.page3.effects", "Synchronized Shield effect will give absorption to player when player heals. " +
				"Player gains same amount of absorption as their healing amount, up to a maximum amount determined by effect level.\n\n" +
				"Every level up doubles maximum absorption and amount of absorption gained");
		pvd.add("arsdelight.page4.effects", "Blast Resistance effect is similar to resistance effect but only applies to explosion damage. " +
				"It stacks multiplicatively with other forms of damage reduction.\n\nEvery level provides 20%% of damage reduction, and at level 5 it makes player immune to explosion damage.");
		pvd.add("arsdelight.page5.effects", "Freezing Spell effect will inflict freezing effect when player's spell hurt another entity.\n\n" +
				"The inflicted freezing effect will have the same level and duration as the Freezing Spell effect currently on player.");
		pvd.add("arsdelight.page6.effects", "Wilden effect will increase player max mana, mana regeneration, and spell damage.\n\n" +
				"By default, each level increases 20%% max mana, mana regen, and spell damage. The number can be tweaked individually in config.");

		// resources
		pvd.add("arsdelight.page.resources", "Natural Resources");
		pvd.add("arsdelight.page1.resources", "Ars Delight adds several ingredients player can collect with tools:" +
				"\n- Archwood Barks" +
				"\n- Wilden Meat" +
				"\n- Chimera Meat" +
				"\n- Chimera Horn");
		pvd.add("arsdelight.page2.resources", "Player can gather archwood barks by cutting archwood logs with axe on cutting table.\n\n" +
				"Archwood barks is widely used in cooking to boost effect of pods from the same archwood tree or convert effect of pods from a different archwood tree");
		pvd.add("arsdelight.page3.resources", "Player can gather Wilden Meat by killing Wilden Hunter with a knife.\n\n" +
				"It's fairly nutritious and is the early game source of wilden effect.");
		pvd.add("arsdelight.page4.resources", "Player can gather Chimera Meat in bulk by killing Chimera with a knife.\n\n" +
				"It's very nutritious and gives strong wilden effect when cooked properly.");
		pvd.add("arsdelight.page5.resources", "Player can gather Chimera Horn by killing Chimera with an axe.\n\n" +
				"It's a reusable food container and can boost food effect.");
	}

	private static DocEntryBuilder of(String id) {
		return new DocEntryBuilder(ArsDelight.MODID, CATEGORY, id, ArsDelight.loc(id));
	}


	private static DocEntryBuilder of(ItemLike item) {
		return new DocEntryBuilder(ArsDelight.MODID, CATEGORY, item);
	}

}
