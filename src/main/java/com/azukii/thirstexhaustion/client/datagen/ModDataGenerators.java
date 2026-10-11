package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ThirstExhaustion.MOD_ID, value = Dist.CLIENT)
public class ModDataGenerators {

    @SubscribeEvent
    static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModSpriteSourceProvider::new);
        event.createProvider(ModDataMapProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModItemTagsProvider::new);
    }
}
