package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.config.WaterBucketRecipeCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ThirstExhaustion.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<WaterBucketRecipeCondition>> WATER_BUCKET_RECIPE_ENABLED = CONDITIONS.register("water_bucket_recipe_enabled", () -> WaterBucketRecipeCondition.CODEC);

    public static void init(IEventBus eventBus) {
        CONDITIONS.register(eventBus);
    }
}
