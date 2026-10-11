package com.azukii.thirstexhaustion.recipe;

import com.azukii.thirstexhaustion.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

public class WaterCollectionRecipe extends SingleItemRecipe {
    public static final MapCodec<WaterCollectionRecipe> MAP_CODEC = simpleMapCodec(WaterCollectionRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, WaterCollectionRecipe> STREAM_CODEC = simpleStreamCodec(WaterCollectionRecipe::new);
    public static final RecipeSerializer<WaterCollectionRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
    private final boolean isFiltered;
    private final int fillingTime;

    public WaterCollectionRecipe(CommonInfo commonInfo, Ingredient input, ItemStackTemplate result, int fillingTime, boolean isFiltered) {
        super(commonInfo, input, result);
        this.fillingTime = fillingTime;
        this.isFiltered = isFiltered;
    }

    public static <T extends WaterCollectionRecipe> MapCodec<T> simpleMapCodec(WaterCollectionRecipe.Factory<T> factory) {
        return RecordCodecBuilder.mapCodec(
                i -> i.group(
                                Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                                Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
                                ItemStackTemplate.CODEC.fieldOf("result").forGetter(WaterCollectionRecipe::result),
                                Codec.INT.fieldOf("filling_time").forGetter(WaterCollectionRecipe::fillingTime),
                                Codec.BOOL.fieldOf("is_filtered").forGetter(WaterCollectionRecipe::isFiltered)
                        )
                        .apply(i, factory::create)
        );
    }

    public static <T extends WaterCollectionRecipe> StreamCodec<RegistryFriendlyByteBuf, T> simpleStreamCodec(WaterCollectionRecipe.Factory<T> factory) {
        return StreamCodec.composite(
                Recipe.CommonInfo.STREAM_CODEC,
                o -> o.commonInfo,
                Ingredient.CONTENTS_STREAM_CODEC,
                SingleItemRecipe::input,
                ItemStackTemplate.STREAM_CODEC,
                WaterCollectionRecipe::result,
                ByteBufCodecs.INT,
                WaterCollectionRecipe::fillingTime,
                ByteBufCodecs.BOOL,
                WaterCollectionRecipe::isFiltered,
                factory::create
        );
    }

    @Override
    public String group() {
        return "";
    }

    public boolean isFiltered() {
        return this.isFiltered;
    }

    public int fillingTime() {
        return this.fillingTime;
    }

    public boolean matchesFilter(boolean filterPresent) {
        return this.isFiltered == filterPresent;
    }

    @Override
    public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
        return ModRecipes.WATER_COLLECTION_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends SingleItemRecipe> getType() {
        return ModRecipes.WATER_COLLECTION_TYPE.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @FunctionalInterface
    public interface Factory<T extends SingleItemRecipe> {
        T create(Recipe.CommonInfo commonInfo, Ingredient ingredient, ItemStackTemplate result, int fillingTime, boolean isFiltered);
    }
}
