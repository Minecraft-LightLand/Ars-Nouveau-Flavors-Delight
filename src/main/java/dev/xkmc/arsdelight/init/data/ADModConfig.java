package dev.xkmc.arsdelight.init.data;

import dev.xkmc.arsdelight.init.ArsDelight;
import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ADModConfig {

	public static class Client extends ConfigInit {

		Client(Builder builder) {
			markPlain();
		}

	}

	public static class Common extends ConfigInit {

		public final ModConfigSpec.BooleanValue enableThirstCompat;

		Common(Builder builder) {
			markPlain();
			enableThirstCompat = builder.text("Enable Thirst Was Taken compatibility").define("enableThirstCompat", true);

		}

	}

	public static class Server extends ConfigInit {

		public final ModConfigSpec.DoubleValue maxShieldingAbsorption;
		public final ModConfigSpec.DoubleValue wildenSpellDamageBonus;
		public final ModConfigSpec.DoubleValue wildenMaxManaBonus;
		public final ModConfigSpec.DoubleValue wildenManaRegenBonus;
		public final ModConfigSpec.BooleanValue drygmyFarmingToolPlainCopy;
		public final ModConfigSpec.IntValue drygmyFarmingDamageTool;

		Server(Builder builder) {
			markPlain();
			maxShieldingAbsorption = builder.text("Max absorption allowed for Shielding I effect. Every level doubles the cap")
					.defineInRange("maxShieldingAbsorption", 8d, 2, 100);
			wildenSpellDamageBonus = builder.text("Wilden effect: spell damage bonus per level")
					.defineInRange("wildenSpellDamageBonus", 0.2d, 0, 1);
			wildenMaxManaBonus = builder.text("Wilden effect: max mana bonus per level")
					.defineInRange("wildenMaxManaBonus", 0.2d, 0, 1);
			wildenManaRegenBonus = builder.text("Wilden effect: mana regen bonus per level")
					.defineInRange("wildenManaRegenBonus", 0.2d, 0, 1);
			drygmyFarmingToolPlainCopy = builder.text("Drygmy farming: make a plain copy of the tool to prevent enchantments, affixes, and gems from taking effect")
					.define("drygmyFarmingToolPlainCopy", false);
			drygmyFarmingDamageTool = builder.text("Drygmy farming: cost durability on the tool used for farming")
					.defineInRange("drygmyFarmingDamageTool", 2, 0, 10000);
		}

	}

	public static final Client CLIENT = ArsDelight.REGISTRATE.registerClient(Client::new);
	public static final Common COMMON = ArsDelight.REGISTRATE.registerUnsynced(Common::new);
	public static final Server SERVER = ArsDelight.REGISTRATE.registerSynced(Server::new);

	public static void init() {
	}


}
