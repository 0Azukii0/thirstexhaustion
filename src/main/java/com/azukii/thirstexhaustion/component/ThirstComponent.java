package com.azukii.thirstexhaustion.component;

import com.azukii.thirstexhaustion.data.ModDataMapProvider;
import com.azukii.thirstexhaustion.data.ModDataMaps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.Nullable;

public record ThirstComponent(int level, WaterDrinkingContext context) implements TooltipComponent {
    public static final Codec<ThirstComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("level").forGetter(ThirstComponent::level),
            WaterDrinkingContext.CODEC.fieldOf("context").forGetter(ThirstComponent::context)
    ).apply(builder, ThirstComponent::new));

    public static final StreamCodec<ByteBuf, ThirstComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ThirstComponent::level,
            WaterDrinkingContext.STREAM_CODEC, ThirstComponent::context,
            ThirstComponent::new
    );

    public static ThirstComponent of(int level) {
        return new ThirstComponent(level, WaterDrinkingContext.CLEAN);
    }

    @Nullable
    public static ThirstComponent get(ItemStack stack) {
        if (!stack.has(DataComponents.CONSUMABLE)) return null;

        ThirstComponent component = stack.get(ModComponents.THIRST);
        if (component != null) return component;

        return ModDataMapProvider.getHolder(stack.getItem()).getData(ModDataMaps.THIRST_REGISTRY_DATA);
    }

    public static WaterDrinkingContext getWaterDrinkingContextFromBlockHitResult(Level level, BlockPos pos) {
        FluidState fluid = level.getFluidState(pos);
        WaterDrinkingContext context = WaterDrinkingContext.DIRTY;
        if (fluid.is(FluidTags.WATER)) {
            Holder<Biome> biome = level.getBiome(pos);
            if (biome.is(BiomeTags.IS_RIVER) && fluid.isSource()) context = WaterDrinkingContext.RIVER;
            if (biome.is(BiomeTags.IS_OCEAN) || biome == Biomes.FROZEN_RIVER) context = WaterDrinkingContext.OCEAN;
            else if (fluid.isSource()) context = WaterDrinkingContext.STILL_WATER;
            if (!fluid.isSource()) context = WaterDrinkingContext.FLOWING_WATER;
        }

        return context;
    }
}
