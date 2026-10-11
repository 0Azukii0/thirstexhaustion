package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.config.WaterBucketRecipeCondition;
import com.azukii.thirstexhaustion.registry.ModBlocks;
import com.azukii.thirstexhaustion.registry.ModItemTags;
import com.azukii.thirstexhaustion.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jspecify.annotations.NonNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
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

        this.shaped(RecipeCategory.MISC, ModItems.WATERSKIN)
                .pattern("SLN")
                .pattern("L L")
                .pattern(" L ")
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .define('N', Tags.Items.NUGGETS)
                .unlockedBy(getHasName(Items.LEATHER), this.has(Items.LEATHER))
                .save(this.output);
        this.shapeless(RecipeCategory.MISC, ModItems.WATERSKIN)
                .requires(ModItems.WATERSKIN)
                .requires(ModItemTags.WATERSKIN_FILTERS)
                .unlockedBy(getHasName(ModItems.WATERSKIN), this.has(ModItems.WATERSKIN))
                .save(this.output, getModdedItemName(ModItems.WATERSKIN, "_with_filter"));
        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WATER_COLLECTOR)
                .pattern("C C")
                .pattern("C C")
                .pattern("CCC")
                .define('C', Blocks.COBBLESTONE)
                .unlockedBy(getHasName(Items.COBBLESTONE), this.has(Items.COBBLESTONE))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItems.CARBON_FILTER)
                .pattern("SCS")
                .define('S', Items.STICK)
                .define('C', ItemTags.COALS)
                .unlockedBy(getHasName(Items.STICK), this.has(Items.STICK))
                .unlockedBy("has_coals", this.has(ItemTags.COALS))
                .save(this.output);

        DataComponentPatch waterBottlePatch = DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER)).build();
        ItemStackTemplate waterBottleStack = new ItemStackTemplate(Items.POTION, waterBottlePatch);
        WaterCollectionRecipeBuilder.generic(Ingredient.of(Items.GLASS_BOTTLE), RecipeCategory.MISC, waterBottleStack, 300, false)
                .unlockedBy(getHasName(ModBlocks.WATER_COLLECTOR), has(ModBlocks.WATER_COLLECTOR))
                .save(this.output, getModdedItemName(waterBottleStack.item().value(), "_from_collection"));
        WaterCollectionRecipeBuilder.generic(Ingredient.of(Items.GLASS_BOTTLE), RecipeCategory.MISC, ModItems.CLEAN_WATER_BOTTLE, 300, true)
                .unlockedBy(getHasName(ModBlocks.WATER_COLLECTOR), has(ModBlocks.WATER_COLLECTOR))
                .save(this.output, getModdedItemName(ModItems.CLEAN_WATER_BOTTLE, "_from_filtration"));
        WaterCollectionRecipeBuilder.generic(Ingredient.of(Items.BOWL), RecipeCategory.MISC, ModItems.WATER_BOWL, 300, false)
                .unlockedBy(getHasName(ModBlocks.WATER_COLLECTOR), has(ModBlocks.WATER_COLLECTOR))
                .save(this.output, getModdedItemName(ModItems.WATER_BOWL, "_from_collection"));
        WaterCollectionRecipeBuilder.generic(Ingredient.of(Items.BOWL), RecipeCategory.MISC, ModItems.CLEAN_WATER_BOWL, 300, true)
                .unlockedBy(getHasName(ModBlocks.WATER_COLLECTOR), has(ModBlocks.WATER_COLLECTOR))
                .save(this.output, getModdedItemName(ModItems.CLEAN_WATER_BOWL, "_from_filtration"));
        WaterCollectionRecipeBuilder.generic(Ingredient.of(Items.BUCKET), RecipeCategory.MISC, Items.WATER_BUCKET, 900, false)
                .unlockedBy(getHasName(ModBlocks.WATER_COLLECTOR), has(ModBlocks.WATER_COLLECTOR))
                .save(this.output.withConditions(new WaterBucketRecipeCondition()), getModdedItemName(Items.WATER_BUCKET, "_from_collection"));
        WaterCollectionRecipeBuilder.generic(Ingredient.of(ModItems.WATERSKIN), RecipeCategory.MISC, ModItems.WATERSKIN, 600, true)
                .unlockedBy(getHasName(ModBlocks.WATER_COLLECTOR), has(ModBlocks.WATER_COLLECTOR))
                .save(this.output, getModdedItemName(ModItems.WATERSKIN, "_from_filtration"));
    }

    protected String getModdedItemName(ItemLike itemLike, String suffix) {
        return ThirstExhaustion.MOD_ID + ":" + getItemName(itemLike) + suffix;
    }
}
