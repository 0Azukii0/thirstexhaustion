package com.azukii.thirstexhaustion.component;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ThirstExhaustion.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ThirstComponent>> THIRST = registerComponent("thirst", ThirstComponent.CODEC, ThirstComponent.STREAM_CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WaterskinComponent>> WATERSKIN = registerComponent("waterskin", WaterskinComponent.CODEC, WaterskinComponent.STREAM_CODEC);

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerComponent(String name, Codec<T> codec, StreamCodec<ByteBuf, T> streamCodec) {
        return COMPONENTS.registerComponentType(
                name,
                builder -> builder
                        .persistent(codec)
                        .networkSynchronized(streamCodec)
                        .cacheEncoding());
    }


    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
