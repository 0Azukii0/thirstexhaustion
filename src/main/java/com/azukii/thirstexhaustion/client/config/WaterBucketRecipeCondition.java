package com.azukii.thirstexhaustion.client.config;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;

public record WaterBucketRecipeCondition() implements ICondition {
    public static final MapCodec<WaterBucketRecipeCondition> CODEC = MapCodec.unit(new WaterBucketRecipeCondition());

    @Override
    public boolean test(IContext context) {
        return ThirstExhaustionCommonConfig.WATER_BUCKET_RECIPE.getAsBoolean();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
