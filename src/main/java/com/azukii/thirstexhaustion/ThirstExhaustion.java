package com.azukii.thirstexhaustion;

import com.azukii.thirstexhaustion.config.ThirstExhaustionCommonConfig;
import com.azukii.thirstexhaustion.config.ThirstExhaustionStartupConfig;
import com.azukii.thirstexhaustion.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import org.slf4j.Logger;

@Mod(ThirstExhaustion.MOD_ID)
@EventBusSubscriber(modid = ThirstExhaustion.MOD_ID)
public class ThirstExhaustion {
    public static final String MOD_ID = "thirstexhaustion";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThirstExhaustion(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerConfig(ModConfig.Type.STARTUP, ThirstExhaustionStartupConfig.CONFIG_SPEC);
        container.registerConfig(ModConfig.Type.SYNCED, ThirstExhaustionCommonConfig.CONFIG_SPEC);

        ModAttachments.init(modEventBus);
        ModComponents.init(modEventBus);
        ModItems.init(modEventBus);
        ModBlocks.init(modEventBus);
        ModBlockEntities.init(modEventBus);
        ModMenus.init(modEventBus);
        ModConditions.init(modEventBus);
        ModRecipes.init(modEventBus);
        LOGGER.info("Thirst Exhaustion System Loaded");
    }

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ModDataMaps.THIRST_REGISTRY_DATA);
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.insertAfter(Items.SPIDER_EYE.getDefaultInstance(), ModItems.CLEAN_WATER_BOWL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.SPIDER_EYE.getDefaultInstance(), ModItems.WATER_BOWL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.POTION.getDefaultInstance(), ModItems.WATERSKIN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.POTION.getDefaultInstance(), ModItems.CLEAN_WATER_BOTTLE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.insertAfter(Items.BOWL.getDefaultInstance(), ModItems.CARBON_FILTER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.insertAfter(Items.SOUL_CAMPFIRE.getDefaultInstance(), ModItems.WATER_COLLECTOR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SubscribeEvent
    public static void modifyDefaultComponentsEvent(ModifyDefaultComponentsEvent event) {
        event.modifyMatching(((_, components) -> components.has(DataComponents.POTION_CONTENTS)),
                (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 3));
        event.modify(Items.MUSHROOM_STEW, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modify(Items.BEETROOT_SOUP, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modify(Items.RABBIT_STEW, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modify(Items.SUSPICIOUS_STEW, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
    }

    @SubscribeEvent
    public static void modifyDefaultComponentsEvent(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntities.WATER_RECUPERATOR_BLOCK_ENTITY.get(),
                (be, direction) -> direction == Direction.UP ? null : new WorldlyContainerWrapper(be, direction)
        );
    }
}
