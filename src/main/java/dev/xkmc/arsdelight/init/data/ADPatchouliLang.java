package dev.xkmc.arsdelight.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class ADPatchouliLang {

	public static void addLang(RegistrateLangProvider pvd) {
		pvd.add("arsdelight.patchouli.category_title", "Ars Delight - Mechanic");
		pvd.add("arsdelight.patchouli.category_desc", "Mechanics, effects, and items worth noting added by Ars Delight that interacts with contents in Ars Nouveau");

		pvd.add("arsdelight.patchouli.drygmy_weapon.title", "Drygmy using Weapon");
		pvd.add("arsdelight.patchouli.drygmy_weapon.page1",
				"With Ars Delight, Drygmy can simulate entity loot with tools on an Arcane Pedestal right next to Drygmy Henge.$(br2)" +
						"This feature allows Drygmy to farm scavenging loot from Farmer's Delight and its addons.");

		pvd.add("arsdelight.patchouli.enchanters_knife",
				"Applies a Touch spell before damaging an entity. Apply a spell at the Scribes Table that does NOT contain a form, such as Ignite -> Extend Time.$(br2)" +
						"Unlike Enchanter's Sword, it does not provide additional Amplify augment.");

		pvd.add("arsdelight.patchouli.jelly.title", "Archwood Pod Jelly");
		pvd.add("arsdelight.patchouli.jelly.page1",
				"Archwood pod jellies are made from archwood fruits and barks, with enhanced fruit effect. Jelly on ground bounces up falling entities and applies jelly effect.$(br2)" +
						"Spell projectiles can pass through jelly blocks. If the spell has infuse effect, override the potion effect, then it won't consume potions.");

		pvd.add("arsdelight.patchouli.automation.title","Spell Automation");
		pvd.add("arsdelight.patchouli.automation.page1","When spells hit a cutting board, it would simulate tools processing items on the cutting board based on the spell effect:" +
				"$(li)Cut: Knife or Shear" +
				"$(li)Cut + Amplify: Axe or Knife" +
				"$(li)Cut + Extract: Shear only" +
				"$(li)Break: Pickaxe" +
				"$(li)Crush: Shovel" +
				"$(li)Fell: Axe$(br)" +
				"Having AOE augment will cancel cutting board automation and proceed with original spell effect.");

		// effects
		pvd.add("arsdelight.patchouli.effects.title", "Food Effects");
		pvd.add("arsdelight.patchouli.effects.page1",
				"By cooking fruits from one archwood with barks from another, one can neutralize the negative effects into something new and activate more beneficial effects.$(br2)" +
						"Food made with wilden drops will provide wilden effect that aids spell casting. Food with better ingredient gives higher level.");
		pvd.add("arsdelight.patchouli.effects.flourishing", "Flourishing effect will restore player mana when player heals. " +
				"When player recovers x%% of their health, this effect will recover same percentage of player mana.$(br2)" +
				"Every level up doubles mana restoration");
		pvd.add("arsdelight.patchouli.effects.synchronized_shield", "Synchronized Shield effect will give absorption to player when player heals. " +
				"Player gains same amount of absorption as their healing amount, up to a maximum amount determined by effect level.$(br2)" +
				"Every level up doubles maximum absorption and amount of absorption gained");
		pvd.add("arsdelight.patchouli.effects.blast_resistance", "Blast Resistance effect is similar to resistance effect but only applies to explosion damage. " +
				"It stacks multiplicatively with other forms of damage reduction.$(br2)Every level provides 20%% of damage reduction, and at level 5 it makes player immune to explosion damage.");
		pvd.add("arsdelight.patchouli.effects.freezing_spell", "Freezing Spell effect will inflict freezing effect when player's spell hurt another entity.$(br2)" +
				"The inflicted freezing effect will have the same level and duration as the Freezing Spell effect currently on player.");
		pvd.add("arsdelight.patchouli.effects.wilden", "Wilden effect will increase player max mana, mana regeneration, and spell damage.$(br2)" +
				"By default, each level increases 20%% max mana, mana regen, and spell damage. The number can be tweaked individually in config.");

		// resources
		pvd.add("arsdelight.patchouli.resources.title", "Natural Resources");
		pvd.add("arsdelight.patchouli.resources.page1", "Ars Delight adds several ingredients player can collect with tools:" +
				"$(li)Archwood Barks" +
				"$(li)Wilden Meat" +
				"$(li)Chimera Meat" +
				"$(li)Chimera Horn");
		pvd.add("arsdelight.patchouli.resources.barks", "Player can gather archwood barks by cutting archwood logs with axe on cutting table.$(br2)" +
				"Archwood barks is widely used in cooking to boost effect of pods from the same archwood tree or convert effect of pods from a different archwood tree");
		pvd.add("arsdelight.patchouli.resources.wilden_meat", "Player can gather Wilden Meat by killing Wilden Hunter with a knife.$(br2)" +
				"It's fairly nutritious and is the early game source of wilden effect.");
		pvd.add("arsdelight.patchouli.resources.chimera_meat", "Player can gather Chimera Meat in bulk by killing Chimera with a knife.$(br2)" +
				"It's very nutritious and gives strong wilden effect when cooked properly.");
		pvd.add("arsdelight.patchouli.resources.chimera_horn", "Player can gather Chimera Horn by killing Chimera with an axe.$(br2)" +
				"It's a reusable food container and can boost food effect.");
	}

}
