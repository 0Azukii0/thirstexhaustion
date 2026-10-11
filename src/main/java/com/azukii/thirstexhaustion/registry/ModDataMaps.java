package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.component.ThirstComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class ModDataMaps {
    public static final DataMapType<Item, ThirstComponent> THIRST_REGISTRY_DATA = DataMapType.builder(
            Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "thirst_registry_data"),
            Registries.ITEM,
            ThirstComponent.CODEC
    ).synced(ThirstComponent.CODEC, true).build();
}
