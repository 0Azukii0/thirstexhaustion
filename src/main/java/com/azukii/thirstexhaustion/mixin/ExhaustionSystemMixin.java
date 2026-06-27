package com.azukii.thirstexhaustion.mixin;

import com.azukii.thirstexhaustion.attachment.ModAttachments;
import com.azukii.thirstexhaustion.component.ThirstData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class ExhaustionSystemMixin {
    @ModifyConstant(
            method = "tick(Lnet/minecraft/server/level/ServerPlayer;)V",
            constant = @Constant(floatValue = 4.0F)
    )
    private float modifyExhaustionThreshold(float original, ServerPlayer player) {
        int thirstLevel = player.getData(ModAttachments.THIRST_DATA).getExhaustionLevel();
        return (float) thirstLevel;
    }

    @Inject(
            method = "tick(Lnet/minecraft/server/level/ServerPlayer;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/food/FoodData;exhaustionLevel:F",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    private void addThirstSystem(ServerPlayer player, CallbackInfo ci) {
        ThirstData thirst = player.getData(ModAttachments.THIRST_DATA);
        thirst.removeThirstLevel(player, 1);
    }
}
