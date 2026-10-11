package com.azukii.thirstexhaustion.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record WaterskinComponent(int thirstLevel, int thirstMaxLevel, int filterLevel) implements TooltipComponent {
    public static final Codec<WaterskinComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("thirstLevel").forGetter(WaterskinComponent::thirstLevel),
            Codec.INT.fieldOf("thirstMaxLevel").forGetter(WaterskinComponent::thirstMaxLevel),
            Codec.INT.fieldOf("filterLevel").forGetter(WaterskinComponent::filterLevel)
    ).apply(builder, WaterskinComponent::new));

    public static final StreamCodec<ByteBuf, WaterskinComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, WaterskinComponent::thirstLevel,
            ByteBufCodecs.INT, WaterskinComponent::thirstMaxLevel,
            ByteBufCodecs.INT, WaterskinComponent::filterLevel,
            WaterskinComponent::new
    );
}
