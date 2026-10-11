package com.azukii.thirstexhaustion.core;

import com.azukii.thirstexhaustion.client.config.ThirstExhaustionCommonConfig;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.IntSupplier;

public enum WaterDrinkingContext {
    CLEAN(ThirstExhaustionCommonConfig.CONFIG.CLEAN),
    DIRTY(ThirstExhaustionCommonConfig.CONFIG.DIRTY),
    RIVER(ThirstExhaustionCommonConfig.CONFIG.RIVER),
    OCEAN(ThirstExhaustionCommonConfig.CONFIG.OCEAN),
    FLOWING_WATER(ThirstExhaustionCommonConfig.CONFIG.FLOWING_WATER),
    STILL_WATER(ThirstExhaustionCommonConfig.CONFIG.STILL_WATER);

    public static final Codec<WaterDrinkingContext> CODEC =
            Codec.STRING.xmap(
                    WaterDrinkingContext::valueOf,
                    WaterDrinkingContext::name
            );

    public static final StreamCodec<ByteBuf, WaterDrinkingContext> STREAM_CODEC =
            ByteBufCodecs.idMapper(
                    i -> WaterDrinkingContext.values()[i],
                    WaterDrinkingContext::ordinal
            );

    private final IntSupplier hungerChance;

    WaterDrinkingContext(IntSupplier hungerChance) {
        this.hungerChance = hungerChance;
    }
}