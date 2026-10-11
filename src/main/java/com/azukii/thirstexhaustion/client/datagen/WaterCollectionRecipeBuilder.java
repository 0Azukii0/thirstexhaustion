package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.recipe.WaterCollectionRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

public class WaterCollectionRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final int fillingTime;
    private final boolean isFiltered;
    private final RecipeUnlockAdvancementBuilder advancementBuilder;
    private final WaterCollectionRecipe.Factory<?> factory;

    private WaterCollectionRecipeBuilder(RecipeCategory category, WaterCollectionRecipe.Factory<?> factory, Ingredient ingredient, ItemStackTemplate result, int fillingTime, boolean isFiltered) {
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
        this.category = category;
        this.result = result;
        this.ingredient = ingredient;
        this.fillingTime = fillingTime;
        this.isFiltered = isFiltered;
        this.factory = factory;
    }

    private WaterCollectionRecipeBuilder(RecipeCategory category, WaterCollectionRecipe.Factory<?> factory, Ingredient ingredient, ItemLike result, int count, int fillingTime, boolean isFiltered) {
        this(category, factory, ingredient, new ItemStackTemplate(result.asItem(), count), fillingTime, isFiltered);
    }

    public static WaterCollectionRecipeBuilder generic(Ingredient ingredient, RecipeCategory category, ItemStackTemplate result, int fillingTime, boolean isFiltered) {
        return new WaterCollectionRecipeBuilder(category, WaterCollectionRecipe::new, ingredient, result, fillingTime, isFiltered);
    }

    public static WaterCollectionRecipeBuilder generic(Ingredient ingredient, RecipeCategory category, ItemLike result, int fillingTime, boolean isFiltered) {
        return new WaterCollectionRecipeBuilder(category, WaterCollectionRecipe::new, ingredient, result, 1, fillingTime, isFiltered);
    }

    @Override
    public WaterCollectionRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public WaterCollectionRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        WaterCollectionRecipe recipe = (WaterCollectionRecipe) this.factory.create(new Recipe.CommonInfo(true), this.ingredient, this.result, this.fillingTime, this.isFiltered);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}
