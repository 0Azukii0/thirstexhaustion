package com.azukii.thirstexhaustion.component;

import com.azukii.thirstexhaustion.ThirstExhaustionConfig;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public enum WaterDrinkingContext {
    CLEAN(ThirstExhaustionConfig.CONFIG.CLEAN.getAsInt()),
    DIRTY(ThirstExhaustionConfig.CONFIG.DIRTY.getAsInt()),
    RIVER(ThirstExhaustionConfig.CONFIG.RIVER.getAsInt()),
    OCEAN(ThirstExhaustionConfig.CONFIG.OCEAN.getAsInt()),
    FLOWING_WATER(ThirstExhaustionConfig.CONFIG.FLOWING_WATER.getAsInt()),
    STILL_WATER(ThirstExhaustionConfig.CONFIG.STILL_WATER.getAsInt());

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

    public final int hungerChance;

    WaterDrinkingContext(int hungerChance) {
        this.hungerChance = hungerChance;
    }
}
