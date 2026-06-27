package com.azukii.thirstexhaustion.mixin.appleskin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import squeek.appleskin.client.HUDOverlayHandler;
import squeek.appleskin.helpers.ColorHelper;

@Mixin(HUDOverlayHandler.class)
public class ExhaustionBarMixin {
    @ModifyExpressionValue(
            method = "drawExhaustionOverlay*",
            at = @At(
                    value = "INVOKE",
                    target = "Lsqueek/appleskin/helpers/ColorHelper;argbFromRGBA(FFFF)I"
            )
    )
    private static int replaceExhaustionColor(int original) {
        return ColorHelper.argbFromRGBA(0.0F, 0.65F, 1.0F, 0.9F);
    }
}
