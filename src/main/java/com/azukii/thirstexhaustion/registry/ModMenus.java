package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.gui.menu.WaterCollectorMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, ThirstExhaustion.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<WaterCollectorMenu>> WATER_RECUPERATOR_MENU = MENUS.register("water_recuperator_menu", () -> new MenuType<>(WaterCollectorMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void init(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
