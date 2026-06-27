package com.azukii.thirstexhaustion.data;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ThirstExhaustion.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.WATER_BOWL.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CLEAN_WATER_BOWL.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CLEAN_WATER_BOTTLE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WATERSKIN.asItem(), ModelTemplates.FLAT_ITEM);
    }
}
