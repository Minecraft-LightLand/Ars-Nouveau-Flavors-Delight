package dev.xkmc.arsdelight.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Locale;

public enum ADLangData {
	CHANCE_EFFECT("tooltip.chance", "%1$s with %2$s%% chance", 2, ChatFormatting.GRAY),
	TOOLTIP_PLACE("tooltip.place", "Shift right click to place down", 0, ChatFormatting.GRAY),
	KNIFE_KILL("tooltip.kill_knife", "Dropped when killing [%s] with knife", 1, ChatFormatting.GRAY),
	AXE_KILL("tooltip.kill_axe", "Dropped when killing [%s] with axe", 1, ChatFormatting.GRAY),
	GRAB("tooltip.grab", "Right click [%1$s] with [%2$s] to obtain", 2, ChatFormatting.GRAY),
	;

	private final String key, def;
	private final int arg;
	private final ChatFormatting format;

	ADLangData(String key, String def, int arg, @Nullable ChatFormatting format) {
		this.key = ArsDelight.MODID + "." + key;
		this.def = def;
		this.arg = arg;
		this.format = format;
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public MutableComponent get(Object... args) {
		if (args.length != arg)
			throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
		MutableComponent ans = Component.translatable(key, args);
		if (format != null) {
			return ans.withStyle(format);
		}
		return ans;
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (ADLangData lang : ADLangData.values()) {
			pvd.add(lang.key, lang.def);
		}
		ADPatchouliLang.addLang(pvd);
	}


}
