package com.azukii.thirstexhaustion.item;

import com.azukii.thirstexhaustion.attachment.ModAttachments;
import com.azukii.thirstexhaustion.component.ModComponents;
import com.azukii.thirstexhaustion.component.ThirstData;
import com.azukii.thirstexhaustion.component.WaterDrinkingContext;
import com.azukii.thirstexhaustion.component.WaterskinComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WaterskinItem extends Item {
    public WaterskinItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 10;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) return InteractionResult.SUCCESS;
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        BlockHitResult hitResult = getPlayerPOVHitResult(context.getLevel(), player, ClipContext.Fluid.WATER);
        if (hitResult.getType() != HitResult.Type.BLOCK) return InteractionResult.PASS;

        FluidState fluidState = context.getLevel().getFluidState(hitResult.getBlockPos());
        if (!fluidState.is(Fluids.WATER) || !fluidState.isSource()) return InteractionResult.PASS;

        ItemStack stack = context.getItemInHand();
        WaterskinComponent waterskin = stack.get(ModComponents.WATERSKIN);
        if (waterskin != null && waterskin.level() < waterskin.maxLevel()) {
            stack.hurtAndBreak(1, player, context.getHand());
            stack.set(ModComponents.WATERSKIN.get(), new WaterskinComponent(waterskin.level()+1, waterskin.maxLevel()));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!(entity instanceof ServerPlayer serverPlayer)) return itemStack;

        WaterskinComponent waterskin = itemStack.get(ModComponents.WATERSKIN.get());
        if (waterskin == null || waterskin.level() == 0) return itemStack;

        ThirstData data = serverPlayer.getData(ModAttachments.THIRST_DATA);
        if (!data.canDrink()) return itemStack;

        itemStack.set(ModComponents.WATERSKIN.get(), new WaterskinComponent(waterskin.level() - 1, waterskin.maxLevel()));
        data.addThirstLevel(serverPlayer, 1, WaterDrinkingContext.CLEAN);

        return itemStack;
    }
}
