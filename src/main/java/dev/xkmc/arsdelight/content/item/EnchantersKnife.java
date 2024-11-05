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
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import vectorwing.farmersdelight.common.item.KnifeItem;

import javax.annotation.Nullable;
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
		super(iItemTier, baseDamage, baseAttackSpeed, properties);
		this.factory = GeckoLibUtil.createInstanceCache(this);
	}

	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean sel) {
		super.inventoryTick(stack, world, entity, slot, sel);
		if (entity instanceof Player player) {
			RepairingPerk.attemptRepair(stack, player);
		}
	}

	public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
		return spell.recipe.stream().noneMatch((s) -> s instanceof AbstractCastMethod);
	}

	public void sendInvalidMessage(Player player) {
		//TODO
		PortUtil.sendMessageNoSpam(player, Component.translatable("ars_nouveau.sword.invalid"));
	}

	public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
		ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
		recipe.add(MethodTouch.INSTANCE);
		recipe.addAll(spell.recipe);
		// Disable amplify
		//recipe.add(AugmentAmplify.INSTANCE);
		spell.recipe = recipe;
		return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity entity) {
		ISpellCaster caster = this.getSpellCaster(stack);
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

	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
		this.getInformation(stack, worldIn, tooltip2, flagIn);
		super.appendHoverText(stack, worldIn, tooltip2, flagIn);
	}

	public void registerControllers(AnimatableManager.ControllerRegistrar animatableManager) {
	}

	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new KnifeRenderer();//Modified

			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	public int getManaDiscount(ItemStack i, Spell spell) {
		return AugmentAmplify.INSTANCE.getCastingCost();
	}

}
