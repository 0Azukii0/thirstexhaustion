package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.component.ThirstComponent;
import com.azukii.thirstexhaustion.core.WaterDrinkingContext;
import com.azukii.thirstexhaustion.registry.ModDataMaps;
import com.azukii.thirstexhaustion.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(ModDataMaps.THIRST_REGISTRY_DATA)
                .add(ModItems.CLEAN_WATER_BOTTLE, ThirstComponent.of(4), false)
                .add(getHolder(Items.POTION), new ThirstComponent(3, WaterDrinkingContext.DIRTY), false)
                .add(getHolder(Items.BEETROOT_SOUP), ThirstComponent.of(3), false)
                .add(getHolder(Items.MUSHROOM_STEW), ThirstComponent.of(3), false)
                .add(getHolder(Items.RABBIT_STEW), ThirstComponent.of(3), false)
                .add(ModItems.CLEAN_WATER_BOWL, ThirstComponent.of(3), false)
                .add(ModItems.WATER_BOWL, new ThirstComponent(2, WaterDrinkingContext.DIRTY), false)
                .add(getHolder(Items.MILK_BUCKET), ThirstComponent.of(2), false)
                .add(getHolder(Items.APPLE), ThirstComponent.of(1), false)
                .add(getHolder(Items.GOLDEN_APPLE), ThirstComponent.of(10), false)
                .add(getHolder(Items.ENCHANTED_GOLDEN_APPLE), ThirstComponent.of(10), false)
                .add(getHolder(Items.SWEET_BERRIES), ThirstComponent.of(1), false)
                .add(getHolder(Items.GLOW_BERRIES), ThirstComponent.of(1), false)
                .add(getHolder(Items.CHORUS_FRUIT), ThirstComponent.of(1), false)
                .add(getHolder(Items.MELON_SLICE), ThirstComponent.of(1), false)
                .add(getHolder(Items.SUSPICIOUS_STEW), ThirstComponent.of(1), false);
    }

    public static Holder<Item> getHolder(Item item) {
        return BuiltInRegistries.ITEM.wrapAsHolder(item);
    }
}
