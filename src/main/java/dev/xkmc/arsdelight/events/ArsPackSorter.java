package dev.xkmc.arsdelight.events;

import dev.xkmc.arsdelight.init.ArsDelight;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class ArsPackSorter {

	public static List<PackResources> sort(List<PackResources> call, PackType type) {
		if (type != PackType.SERVER_DATA) return call;
		var ans = new ArrayList<PackResources>();
		var ars = new TreeMap<Integer, PackResources>(Comparator.comparingInt(i -> -i));
		for (var e : call) {
			var set = e.getNamespaces(type);
			if (set.contains(ArsDelight.MODID)) {
				ars.put(set.size(), e);
			} else {
				ans.add(e);
			}
		}
		ans.addAll(ars.values());
		return ans;
	}

}
