package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.client.config.ThirstExhaustionCommonConfig;
import com.azukii.thirstexhaustion.client.config.ThirstExhaustionStartupConfig;
import com.azukii.thirstexhaustion.registry.ModBlocks;
import com.azukii.thirstexhaustion.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ThirstExhaustion.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.THIRST_LEVEL_MAX, "Thirst Max Level", "Maximum thirst value for the player");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.THIRST_LEVEL_MIN, "Thirst Min Level", "Minimum thirst value for the player");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MAX_LEVEL, "Exhaustion Max Level", "Maximum exhaustion value for the player");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MIN_LEVEL_NORMAL, "Exhaustion Min Level - Normal & Peaceful", "Minimum exhaustion value for the player in peaceful and normal mode");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MIN_LEVEL_HARDCORE, "Exhaustion Min Level - Hardcore", "Minimum exhaustion value for the player in hardcore mode");

        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.CLEAN, "Clean Water Hunger Chance", "Hunger chance effect in percent from clean water");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.DIRTY, "Dirty Water Hunger Chance", "Hunger chance effect in percent from clean water");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.RIVER, "River Water Hunger Chance", "Hunger chance effect in percent from river water");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.OCEAN, "Ocean Water Hunger Chance", "Hunger chance effect in percent from ocean water");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.FLOWING_WATER, "Flowing Water Hunger Chance", "Hunger chance effect in percent from flowing water");
        this.addConfiguration(ThirstExhaustionCommonConfig.CONFIG.STILL_WATER, "Still Water Hunger Chance", "Hunger chance effect in percent from clean water");

        this.addConfiguration(ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_DURABILITY, "Waterskin Durability", "Waterskin durability");
        this.addConfiguration(ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_MAX_LEVEL, "Waterskin Max Level", "Waterskin maximum contained thirst thirstlevel");

        this.addConfiguration(ThirstExhaustionCommonConfig.WATER_BUCKET_RECIPE, "Water Bucket Recipe", "Enable the water bucket recipe in the water collector");

        this.addSection("thirst", "Thirst", "Thirst thirstlevel settings");
        this.addSection("exhaustion", "Exhaustion", "Exhaustion thirstlevel settings");
        this.addSection("hungerChance", "Hunger Chance", "Hunger effect chances per water type");
        this.addSection("waterskin", "Waterskin", "Waterskin settings");
        this.addSection("recipes", "Recipes", "Recipe toggles");

        this.addItem(ModItems.WATER_BOWL, "Water Bowl");
        this.addItem(ModItems.CLEAN_WATER_BOWL, "Clean Water Bowl");
        this.addItem(ModItems.CLEAN_WATER_BOTTLE, "Clean Water Bottle");
        this.addCleanWaterBottleTranslations();
        this.addItem(ModItems.WATERSKIN, "Waterskin");
        this.addBlock(ModBlocks.WATER_COLLECTOR, "Water Collector");
        this.addItem(ModItems.CARBON_FILTER, "Carbon Filter");

        this.add("thirstexhaustion.container.water_collector", "Water Collector");

        this.add("thirstexhaustion.tooltip.waterskin", "Filter Uses : %s");
    }

    private void addCleanWaterBottleTranslations() {
        this.add("item.thirstexhaustion.clean_water_bottle.effect.empty", "Uncraftable Clean Water Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.water", "Clean Water Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.awkward", "Awkward Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.mundane", "Mundane Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.thick", "Thick Bottle");
    }

    public void addConfiguration(ModConfigSpec.ConfigValue<?> configSpec, String name, String tooltip) {
        String key = "thirstexhaustion.configuration." + configSpec.getPath().getLast();
        this.add(key, name);
        this.add(key + ".tooltip", tooltip);
    }

    public void addSection(String section, String name, String tooltip) {
        String key = "thirstexhaustion.configuration." + section;
        this.add(key, name);
        this.add(key + ".tooltip", tooltip);
    }
}
