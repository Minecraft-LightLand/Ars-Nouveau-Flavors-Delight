package organize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LangFileOrganizer extends ResourceOrganizer {

	public LangFileOrganizer() {
		super(Type.ASSETS, "lang", "lang/");
	}

	@Override
	public void organize(File f) throws Exception {
		for (File fi : f.listFiles()) {
			if (!fi.isDirectory())
				continue;
			String name = fi.getName();
			File target = new File(getTargetFolder() + name + ".json");
			check(target);
			JsonObject dst_json = new JsonObject();
			for (File fj : fi.listFiles()) {
				if (!fj.getName().endsWith(".json")) continue;
				JsonObject json = new JsonParser().parse(new FileReader(fj.getPath(), StandardCharsets.UTF_8)).getAsJsonObject();
				inject("", json, dst_json);
				if (json.has("-cartesian")) {
					JsonObject block_list = json.get("-cartesian").getAsJsonObject();
					block_list.entrySet().forEach(ent0 -> {
						JsonObject block = ent0.getValue().getAsJsonObject();
						String path = block.get("path").getAsString();
						boolean reverse = block.has("reverse") && block.get("reverse").getAsBoolean();
						boolean dot = block.has("use_dot") && block.get("use_dot").getAsBoolean();
						String con = dot ? "." : "_";
						List<Pair<String, String>> map = new ArrayList<>();
						for (JsonElement vector : block.get("list").getAsJsonArray()) {
							if (map.isEmpty()) {
								List<Pair<String, String>> finalMap = map;
								vector.getAsJsonObject().entrySet().forEach(ent1 ->
										finalMap.add(Pair.of(ent1.getKey(), ent1.getValue().getAsString())));
							} else {
								map = map.stream().flatMap(ent1 -> vector.getAsJsonObject().entrySet().stream()
												.map(ent2 -> connect(ent1, ent2, reverse, con)))
										.collect(Collectors.toList());
							}
						}
						for (Pair<String, String> pair : map) {
							dst_json.addProperty((path.isEmpty() ? "" : path + ".") + pair.getFirst(), pair.getSecond());
						}

					});
				}
			}
			FileWriter w = new FileWriter(target, StandardCharsets.UTF_8);
			w.write(GSON.toJson(dst_json));
			w.close();
		}
	}

	private Pair<String, String> connect(Pair<String, String> ent1, Map.Entry<String, JsonElement> ent2, boolean reverse, String con) {
		String k1 = ent1.getFirst();
		String k2 = ent2.getKey();
		String key = k2.contains("*") ? k2.replaceFirst("\\*", k1) : k1.isEmpty() ? k2 : k1 + con + k2;
		String v1 = ent1.getSecond();
		String v2 = ent2.getValue().getAsString();
		String val = v2.contains("*") ? v2.replaceFirst("\\*", v1) : reverse ? v2 + v1 : v1 + v2;
		return Pair.of(key, val);
	}

	private void inject(String path, JsonObject src, JsonObject dst) {
		for (Map.Entry<String, JsonElement> ent : src.entrySet()) {
			if (ent.getKey().startsWith("-")) continue;
			if (ent.getValue().isJsonObject()) {
				inject(path + ent.getKey() + ".", ent.getValue().getAsJsonObject(), dst);
			} else {
				dst.add(path + ent.getKey(), ent.getValue());
			}
		}
	}

}
