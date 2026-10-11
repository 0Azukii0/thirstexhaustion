package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.client.config.ThirstExhaustionStartupConfig;
import com.azukii.thirstexhaustion.component.WaterskinComponent;
import com.azukii.thirstexhaustion.item.WaterskinItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThirstExhaustion.MOD_ID);

    public static final DeferredItem<Item> WATER_BOWL = ITEMS.registerSimpleItem("water_bowl", (properties) -> properties
            .stacksTo(16)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.BOWL));
    public static final DeferredItem<Item> CLEAN_WATER_BOWL = ITEMS.registerSimpleItem("clean_water_bowl", (properties) -> properties
            .stacksTo(16)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.BOWL));
    public static final DeferredItem<Item> CLEAN_WATER_BOTTLE = ITEMS.registerSimpleItem("clean_water_bottle", (properties) -> properties
            .stacksTo(16)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.GLASS_BOTTLE));
    public static final DeferredItem<Item> WATERSKIN = ITEMS.registerItem("waterskin", WaterskinItem::new, (properties) -> properties
            .component(DataComponents.CONSUMABLE, new Consumable(1.0f, ItemUseAnimation.DRINK, SoundEvents.GENERIC_DRINK, false, List.of()))
            .durability(ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_DURABILITY.getAsInt())
            .component(ModComponents.WATERSKIN, new WaterskinComponent(0, ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_MAX_LEVEL.getAsInt(), 0)));
    public static final DeferredItem<BlockItem> WATER_COLLECTOR = ITEMS.registerSimpleBlockItem(ModBlocks.WATER_COLLECTOR);
    public static final DeferredItem<Item> CARBON_FILTER = ITEMS.registerSimpleItem("carbon_filter", (properties) -> properties.durability(32));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
