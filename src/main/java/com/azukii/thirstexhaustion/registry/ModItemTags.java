package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> FILTERS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "filters"));
    public static final TagKey<Item> WATER_COLLECTOR_FILTERS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "water_collector_filters"));
    public static final TagKey<Item> WATERSKIN_FILTERS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "waterskin_filters"));
}
