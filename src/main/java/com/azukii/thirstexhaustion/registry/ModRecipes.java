package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.recipe.WaterCollectionRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ThirstExhaustion.MOD_ID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WaterCollectionRecipe>> WATER_COLLECTION_SERIALIZER = RECIPE_SERIALIZERS.register("water_collection", () -> WaterCollectionRecipe.SERIALIZER);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ThirstExhaustion.MOD_ID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<WaterCollectionRecipe>> WATER_COLLECTION_TYPE = RECIPE_TYPES.register("water_collection", RecipeType::simple);

    public static void init(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
