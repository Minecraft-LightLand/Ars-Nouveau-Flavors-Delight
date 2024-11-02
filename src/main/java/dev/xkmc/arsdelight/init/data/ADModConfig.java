package dev.xkmc.arsdelight.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ADModConfig {

	public static class Client {

		Client(ForgeConfigSpec.Builder builder) {
		}

	}

	public static class Common {

		public final ForgeConfigSpec.BooleanValue enableThirstCompat;
		public final ForgeConfigSpec.DoubleValue maxShieldingAbsorption;
		public final ForgeConfigSpec.DoubleValue wildenSpellDamageBonus;
		public final ForgeConfigSpec.DoubleValue wildenMaxManaBonus;
		public final ForgeConfigSpec.DoubleValue wildenManaRegenBonus;

		Common(ForgeConfigSpec.Builder builder) {
			enableThirstCompat = builder.define("enableThirstCompat", true);
			maxShieldingAbsorption = builder.comment("Max absorption allowed for Shielding I effect. Every level doubles the cap")
					.defineInRange("maxShieldingAbsorption", 8d, 2, 100);
			wildenSpellDamageBonus = builder.comment("Wilden effect: spell damage bonus per level")
					.defineInRange("wildenSpellDamageBonus", 0.2d, 0, 1);
			wildenMaxManaBonus = builder.comment("Wilden effect: max mana bonus per level")
					.defineInRange("wildenMaxManaBonus", 0.2d, 0, 1);
			wildenManaRegenBonus = builder.comment("Wilden effect: mana regen bonus per level")
					.defineInRange("wildenManaRegenBonus", 0.2d, 0, 1);

		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
	}


}
