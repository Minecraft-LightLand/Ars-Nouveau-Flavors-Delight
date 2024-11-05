package dev.xkmc.arsdelight.content.item;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.mana.IManaDiscountEquipment;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// Copied from EnchantersSword
public class EnchantersKnife extends KnifeItem implements ICasterTool, GeoItem, IManaDiscountEquipment {
	public AnimatableInstanceCache factory;

	public EnchantersKnife(Tier iItemTier, int baseDamage, float baseAttackSpeed) {
		this(iItemTier, baseDamage, baseAttackSpeed, ItemsRegistry.defaultItemProperties().stacksTo(1));
	}

	public EnchantersKnife(Tier iItemTier, int baseDamage, float baseAttackSpeed, Item.Properties properties) {
		super(iItemTier, properties
				.component(DataComponents.ATTRIBUTE_MODIFIERS, KnifeItem.createAttributes(iItemTier, baseDamage, baseAttackSpeed))
				.component(DataComponentRegistry.SPELL_CASTER, new SpellCaster()));
		this.factory = GeckoLibUtil.createInstanceCache(this);
	}

	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.inventoryTick(stack, world, entity, p_77663_4_, p_77663_5_);
		if (entity instanceof Player player) {
			RepairingPerk.attemptRepair(stack, player);
		}

	}

	public boolean isScribedSpellValid(AbstractCaster<?> caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
		return spell.unsafeList().stream().noneMatch((s) -> s instanceof AbstractCastMethod);
	}

	public void sendInvalidMessage(Player player) {
		//TODO
		PortUtil.sendMessageNoSpam(player, Component.translatable("ars_nouveau.sword.invalid"));
	}

	public void scribeModifiedSpell(AbstractCaster<?> caster, Player player, InteractionHand hand, ItemStack stack, Spell.Mutable spell) {
		ArrayList<AbstractSpellPart> recipe = new ArrayList();
		recipe.add(MethodTouch.INSTANCE);
		recipe.addAll(spell.recipe);
		// Disable amplify
		//recipe.add(AugmentAmplify.INSTANCE);
		spell.recipe = recipe;
	}

	public boolean hurtEnemy(@NotNull ItemStack stack, LivingEntity target, @NotNull LivingEntity entity) {
		AbstractCaster<?> caster = this.getSpellCaster(stack);
		if (caster == null) return false;
		IWrappedCaster wrappedCaster;
		if (entity instanceof Player player) {
			wrappedCaster = new PlayerCaster(player);
		} else {
			wrappedCaster = new LivingCaster(entity);
		}
		SpellContext context = new SpellContext(entity.level(), caster.modifySpellBeforeCasting(target.level(), entity, InteractionHand.MAIN_HAND, caster.getSpell()), entity, wrappedCaster, stack);
		SpellResolver resolver = entity instanceof Player ? new SpellResolver(context) : new EntitySpellResolver(context);
		EntityHitResult entityRes = new EntityHitResult(target);
		resolver.onCastOnEntity(stack, entityRes.getEntity(), InteractionHand.MAIN_HAND);
		return super.hurtEnemy(stack, target, entity);
	}

	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.@NotNull TooltipContext context, @NotNull List<Component> tooltip2, @NotNull TooltipFlag flagIn) {
		this.getInformation(stack, context, tooltip2, flagIn);
		super.appendHoverText(stack, context, tooltip2, flagIn);
	}

	public void registerControllers(AnimatableManager.ControllerRegistrar animatableManager) {
	}

	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			final KnifeRenderer renderer = new KnifeRenderer();

			public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
				return this.renderer;
			}
		});
	}

	public int getManaDiscount(ItemStack i, Spell spell) {
		return AugmentAmplify.INSTANCE.getCastingCost();
	}
}
