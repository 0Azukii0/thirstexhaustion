package com.azukii.thirstexhaustion.data;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.WATER_BOWL), RecipeCategory.MISC, ModItems.CLEAN_WATER_BOWL, 0.35F, 100)
                .unlockedBy(getHasName(ModItems.WATER_BOWL), this.has(ModItems.WATER_BOWL))
                .save(this.output, getModdedItemName(ModItems.CLEAN_WATER_BOWL, "_from_campfire_cooking"));

        Ingredient waterBottle = DataComponentIngredient.of(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION);
        SimpleCookingRecipeBuilder.campfireCooking(waterBottle, RecipeCategory.MISC, ModItems.CLEAN_WATER_BOTTLE, 0.35F, 300)
                .unlockedBy(getHasName(Items.POTION), this.has(Items.POTION))
                .save(this.output, getModdedItemName(ModItems.CLEAN_WATER_BOTTLE, "_from_campfire_cooking"));
        SimpleCookingRecipeBuilder.smelting(waterBottle, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.CLEAN_WATER_BOTTLE, 0.35F, 100)
                .unlockedBy(getHasName(Items.POTION), this.has(Items.POTION))
                .save(this.output, getModdedItemName(ModItems.CLEAN_WATER_BOTTLE, "_from_smelting"));

        DataComponentPatch patch = DataComponentPatch.builder().set(DataComponents.DAMAGE, 3).build();
        ItemStackTemplate waterskinStack = new ItemStackTemplate(ModItems.WATERSKIN, patch);
        this.shaped(RecipeCategory.FOOD, waterskinStack)
                .pattern("SLN")
                .pattern("LCL")
                .pattern(" L ")
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .define('C', Items.CHARCOAL)
                .define('N', Tags.Items.NUGGETS)
                .unlockedBy(getHasName(Items.STRING), this.has(Items.STRING))
                .unlockedBy(getHasName(Items.LEATHER), this.has(Items.LEATHER))
                .unlockedBy(getHasName(Items.CHARCOAL), this.has(Items.CHARCOAL))
                .unlockedBy("has_nuggets", this.has(Tags.Items.NUGGETS))
                .save(this.output);
    }

    protected String getModdedItemName(ItemLike itemLike, String suffix) {
        return ThirstExhaustion.MODID + ":" + getItemName(itemLike) + suffix;
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Mod Recipe JSONs";
        }
    }
}
