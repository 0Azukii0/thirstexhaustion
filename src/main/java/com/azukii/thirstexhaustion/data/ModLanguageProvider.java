package com.azukii.thirstexhaustion.data;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.ThirstExhaustionConfig;
import com.azukii.thirstexhaustion.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ThirstExhaustion.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("thirstexhaustion.configuration.title", "Thirst Exhaustion Configs");
        this.add("thirstexhaustion.configuration.section.thirstexhaustion.common.toml", "Thirst Exhaustion Configs");
        this.add("thirstexhaustion.configuration.section.thirstexhaustion.common.toml.title", "Thirst Exhaustion Configs");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.THIRST_LEVEL_MAX, "Thirst Max Level", "Maximum thirst value for the player");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.THIRST_LEVEL_MIN, "Thirst Min Level", "Minimum thirst value for the player");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.EXHAUSTION_MAX_LEVEL, "Exhaustion Max Level", "Maximum exhaustion value for the player");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.EXHAUSTION_MIN_LEVEL_NORMAL, "Exhaustion Min Level - Normal & Peaceful", "Minimum exhaustion value for the player in peaceful and normal mode");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.EXHAUSTION_MIN_LEVEL_HARDCORE, "Exhaustion Min Level - Hardcore", "Minimum exhaustion value for the player in hardcore mode");

        this.addConfiguration(ThirstExhaustionConfig.CONFIG.CLEAN, "Clean Water Hunger Chance", "Hunger chance effect in percent from clean water");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.DIRTY, "Dirty Water Hunger Chance", "Hunger chance effect in percent from clean water");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.RIVER, "River Water Hunger Chance", "Hunger chance effect in percent from river water");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.OCEAN, "Ocean Water Hunger Chance", "Hunger chance effect in percent from ocean water");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.FLOWING_WATER, "Flowing Water Hunger Chance", "Hunger chance effect in percent from flowing water");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.STILL_WATER, "Still Water Hunger Chance", "Hunger chance effect in percent from clean water");

        this.addConfiguration(ThirstExhaustionConfig.CONFIG.WATERSKIN_DURABILITY, "Waterskin Durability", "Waterskin durability");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.WATERSKIN_USE_COOLDOWN, "Waterskin Use Cooldown", "Waterskin use cooldown");
        this.addConfiguration(ThirstExhaustionConfig.CONFIG.WATERSKIN_MAX_LEVEL, "Waterskin Max Level", "Waterskin maximum contained thirst level");

        this.addItem(ModItems.WATER_BOWL, "Water Bowl");
        this.addItem(ModItems.CLEAN_WATER_BOWL, "Clean Water Bowl");
        this.addItem(ModItems.CLEAN_WATER_BOTTLE, "Clean Water Bottle");
        this.addCleanWaterBottleTranslations();
        this.addItem(ModItems.WATERSKIN, "Waterskin");
    }

    private void addCleanWaterBottleTranslations() {
        this.add("item.thirstexhaustion.clean_water_bottle.effect.empty", "Uncraftable Clean Water Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.water", "Clean Water Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.awkward", "Awkward Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.mundane", "Mundane Bottle");
        this.add("item.thirstexhaustion.clean_water_bottle.effect.thick", "Thick Bottle");
    }

    public void addConfiguration(ModConfigSpec.IntValue configSpec, String name, String tooltip) {
        this.add("thirstexhaustion.configuration."+configSpec.getPath().getFirst(), name);
        this.add("thirstexhaustion.configuration."+configSpec.getPath().getFirst()+".tooltip", tooltip);
    }

    public void addConfiguration(ModConfigSpec.DoubleValue configSpec, String name, String tooltip) {
        this.add("thirstexhaustion.configuration."+configSpec.getPath().getFirst(), name);
        this.add("thirstexhaustion.configuration."+configSpec.getPath().getFirst()+".tooltip", tooltip);
    }
}
