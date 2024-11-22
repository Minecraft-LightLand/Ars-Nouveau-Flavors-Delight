package dev.xkmc.arsdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.xkmc.arsdelight.events.ArsPackSorter;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.List;

@Mixin(MultiPackResourceManager.class)
public class MultiPackResourceManagerMixin {

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;copyOf(Ljava/util/Collection;)Ljava/util/List;"))
	private List<PackResources> arsDelight$sortPacks(
			Collection<PackResources> coll,
			Operation<List<PackResources>> original,
			@Local(argsOnly = true) PackType type,
			@Local(argsOnly = true) LocalRef<List<PackResources>> pPacks
	) {
		var ans = ArsPackSorter.sort(original.call(coll), type);
		pPacks.set(ans);
		return ans;
	}


}
