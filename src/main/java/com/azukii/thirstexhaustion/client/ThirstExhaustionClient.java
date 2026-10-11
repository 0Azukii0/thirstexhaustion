package com.azukii.thirstexhaustion.client;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.client.screen.WaterCollectorScreen;
import com.azukii.thirstexhaustion.component.ThirstComponent;
import com.azukii.thirstexhaustion.component.ThirstTooltipComponent;
import com.azukii.thirstexhaustion.component.WaterskinComponent;
import com.azukii.thirstexhaustion.component.WaterskinTooltipComponent;
import com.azukii.thirstexhaustion.registry.ModComponents;
import com.azukii.thirstexhaustion.registry.ModMenus;
import com.mojang.datafixers.util.Either;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ThirstExhaustion.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ThirstExhaustion.MOD_ID, value = Dist.CLIENT)
public class ThirstExhaustionClient {
    public ThirstExhaustionClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onGatherComponents(RenderTooltipEvent.GatherComponents event) {
        ThirstComponent thirst = ThirstComponent.get(event.getItemStack());
        if (thirst != null && thirst.level() > 0) {
            event.getTooltipElements().add(Either.right(thirst));
        }

        WaterskinComponent waterskin = event.getItemStack().get(ModComponents.WATERSKIN);
        if (waterskin != null) {
            event.getTooltipElements().add(Either.right(waterskin));
        }
    }

    @SubscribeEvent
    static void registerCMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.WATER_RECUPERATOR_MENU.get(), WaterCollectorScreen::new);
    }

    @SubscribeEvent
    static void registerClientTooltipComponent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ThirstComponent.class, ThirstTooltipComponent::new);
        event.register(WaterskinComponent.class, WaterskinTooltipComponent::new);
    }
}
