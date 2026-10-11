package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.registry.ModItemTags;
import com.azukii.thirstexhaustion.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ThirstExhaustion.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModItemTags.FILTERS)
                .addTag(ModItemTags.WATER_COLLECTOR_FILTERS)
                .addTag(ModItemTags.WATERSKIN_FILTERS);
        this.tag(ModItemTags.WATER_COLLECTOR_FILTERS)
                .add(ModItems.CARBON_FILTER.getKey());
        this.tag(ModItemTags.WATERSKIN_FILTERS)
                .add(ModItems.CARBON_FILTER.getKey());
    }
}
