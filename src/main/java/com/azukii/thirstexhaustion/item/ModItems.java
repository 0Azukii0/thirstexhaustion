package com.azukii.thirstexhaustion.item;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.ThirstExhaustionConfig;
import com.azukii.thirstexhaustion.component.ModComponents;
import com.azukii.thirstexhaustion.component.WaterskinComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThirstExhaustion.MODID);

    public static final DeferredItem<Item> WATER_BOWL = ITEMS.registerSimpleItem("water_bowl", (properties) -> properties
            .stacksTo(16)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.BOWL));
    public static final DeferredItem<Item> CLEAN_WATER_BOWL = ITEMS.registerSimpleItem("clean_water_bowl", (properties) -> properties
            .stacksTo(16)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.BOWL));
    public static final DeferredItem<Item> CLEAN_WATER_BOTTLE = ITEMS.registerSimpleItem("clean_water_bottle", (properties) -> properties
            .stacksTo(3)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.GLASS_BOTTLE));
    public static final DeferredItem<Item> WATERSKIN = ITEMS.registerItem("waterskin", WaterskinItem::new, (properties) -> properties
            .stacksTo(1)
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .durability(ThirstExhaustionConfig.CONFIG.WATERSKIN_DURABILITY.getAsInt())
            .useCooldown((float) ThirstExhaustionConfig.CONFIG.WATERSKIN_USE_COOLDOWN.getAsDouble())
            .component(ModComponents.WATERSKIN, new WaterskinComponent(0,ThirstExhaustionConfig.CONFIG.WATERSKIN_MAX_LEVEL.getAsInt())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
