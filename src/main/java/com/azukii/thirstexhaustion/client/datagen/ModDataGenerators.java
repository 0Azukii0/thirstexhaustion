package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

@EventBusSubscriber(modid = ThirstExhaustion.MOD_ID, value = Dist.CLIENT)
public class ModDataGenerators {

    @SubscribeEvent
    static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModSpriteSourceProvider::new);
        event.createProvider(ModDataMapProvider::new);
        event.createProvider((output, _) -> ModLanguageProvider.english(output));
        event.createProvider((output, _) -> ModLanguageProvider.french(output));
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModItemTagsProvider::new);
    }

    @SubscribeEvent
    static void gatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.add(RecipeProvider.asBootstrap(ModRecipeProvider::new));
    }
}
