package com.azukii.thirstexhaustion.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record WaterskinComponent(int level, int maxLevel) implements TooltipComponent {
    public static final Codec<WaterskinComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("level").forGetter(WaterskinComponent::level),
            Codec.INT.fieldOf("maxLevel").forGetter(WaterskinComponent::maxLevel)
    ).apply(builder, WaterskinComponent::new));

    public static final StreamCodec<ByteBuf, WaterskinComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, WaterskinComponent::level,
            ByteBufCodecs.INT, WaterskinComponent::maxLevel,
            WaterskinComponent::new
    );
}
