package com.azukii.thirstexhaustion;

import com.azukii.thirstexhaustion.component.*;
import com.mojang.datafixers.util.Either;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ThirstExhaustion.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ThirstExhaustion.MODID, value = Dist.CLIENT)
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
    static void onGatherWaterskin(RenderTooltipEvent.GatherComponents event) {
    }

    @SubscribeEvent
    static void registerClientTooltipComponent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ThirstComponent.class, ThirstTooltipComponent::new);
        event.register(WaterskinComponent.class, WaterskinTooltipComponent::new);
    }
}
