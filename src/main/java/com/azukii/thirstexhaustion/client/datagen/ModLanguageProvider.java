package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.config.ThirstExhaustionCommonConfig;
import com.azukii.thirstexhaustion.config.ThirstExhaustionStartupConfig;
import com.azukii.thirstexhaustion.registry.ModBlocks;
import com.azukii.thirstexhaustion.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    private final boolean fr;

    private ModLanguageProvider(PackOutput output, String locale) {
        super(output, ThirstExhaustion.MOD_ID, locale);
        this.fr = locale.equals("fr_fr");
    }

    public static ModLanguageProvider english(PackOutput output) {
        return new ModLanguageProvider(output, "en_us");
    }

    public static ModLanguageProvider french(PackOutput output) {
        return new ModLanguageProvider(output, "fr_fr");
    }

    private String translate(String en, String frText) {
        return fr ? frText : en;
    }

    @Override
    protected void addTranslations() {
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.THIRST_LEVEL_MAX,
                translate("Thirst Max Level", "Niveau de soif maximum"),
                translate("Maximum thirst value for the player", "Valeur de soif maximale du joueur"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.THIRST_LEVEL_MIN,
                translate("Thirst Min Level", "Niveau de soif minimum"),
                translate("Minimum thirst value for the player", "Valeur de soif minimale du joueur"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MAX_LEVEL,
                translate("Exhaustion Max Level", "Niveau d'épuisement maximum"),
                translate("Maximum exhaustion value for the player", "Valeur d'épuisement maximale du joueur"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MIN_LEVEL_NORMAL,
                translate("Exhaustion Min Level - Normal & Peaceful", "Niveau d'épuisement minimum - Normal et Pacifique"),
                translate("Minimum exhaustion value for the player in peaceful and normal mode", "Valeur d'épuisement minimale du joueur en mode pacifique et normal"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MIN_LEVEL_HARDCORE,
                translate("Exhaustion Min Level - Hardcore", "Niveau d'épuisement minimum - Hardcore"),
                translate("Minimum exhaustion value for the player in hardcore mode", "Valeur d'épuisement minimale du joueur en mode hardcore"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.CLEAN,
                translate("Clean Water Hunger Chance", "Chance de faim - Eau potable"),
                translate("Hunger chance effect in percent from clean water", "Probabilité de l'effet de faim, en pourcentage, avec l'eau potable"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.DIRTY,
                translate("Dirty Water Hunger Chance", "Chance de faim - Eau sale"),
                translate("Hunger chance effect in percent from dirty water", "Probabilité de l'effet de faim, en pourcentage, avec l'eau sale"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.RIVER,
                translate("River Water Hunger Chance", "Chance de faim - Eau de rivière"),
                translate("Hunger chance effect in percent from river water", "Probabilité de l'effet de faim, en pourcentage, avec l'eau de rivière"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.OCEAN,
                translate("Ocean Water Hunger Chance", "Chance de faim - Eau de l'océan"),
                translate("Hunger chance effect in percent from ocean water", "Probabilité de l'effet de faim, en pourcentage, avec l'eau de l'océan"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.FLOWING_WATER,
                translate("Flowing Water Hunger Chance", "Chance de faim - Eau courante"),
                translate("Hunger chance effect in percent from flowing water", "Probabilité de l'effet de faim, en pourcentage, avec l'eau courante"));
        addConfiguration(ThirstExhaustionCommonConfig.CONFIG.STILL_WATER,
                translate("Still Water Hunger Chance", "Chance de faim - Eau stagnante"),
                translate("Hunger chance effect in percent from still water", "Probabilité de l'effet de faim, en pourcentage, avec l'eau stagnante"));
        addConfiguration(ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_DURABILITY,
                translate("Waterskin Durability", "Durabilité de la gourde"),
                translate("Waterskin durability", "Durabilité de la gourde"));
        addConfiguration(ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_MAX_LEVEL,
                translate("Waterskin Max Level", "Niveau maximum de la gourde"),
                translate("Waterskin maximum contained thirst level", "Niveau de soif maximal que la gourde peut contenir"));
        addConfiguration(ThirstExhaustionCommonConfig.WATER_BUCKET_RECIPE,
                translate("Water Bucket Recipe", "Recette du seau d'eau"),
                translate("Enable the water bucket recipe in the water collector", "Active la recette du seau d'eau dans le collecteur d'eau"));

        addSection("thirst",
                translate("Thirst", "Soif"),
                translate("Thirst level settings", "Paramètres du niveau de soif"));
        addSection("exhaustion",
                translate("Exhaustion", "Épuisement"),
                translate("Exhaustion level settings", "Paramètres du niveau d'épuisement"));
        addSection("hungerChance",
                translate("Hunger Chance", "Chance de faim"),
                translate("Hunger effect chances per water type", "Probabilités de l'effet de faim selon le type d'eau"));
        addSection("waterskin",
                translate("Waterskin", "Gourde"),
                translate("Waterskin settings", "Paramètres de la gourde"));
        addSection("recipes",
                translate("Recipes", "Recettes"),
                translate("Recipe toggles", "Activation des recettes"));

        addItem(ModItems.WATER_BOWL, translate("Water Bowl", "Bol d'eau"));
        addItem(ModItems.CLEAN_WATER_BOWL, translate("Clean Water Bowl", "Bol d'eau potable"));
        addItem(ModItems.CLEAN_WATER_BOTTLE, translate("Clean Water Bottle", "Bouteille d'eau potable"));
        addCleanWaterBottleTranslations();
        addItem(ModItems.WATERSKIN, translate("Waterskin", "Gourde"));
        addBlock(ModBlocks.WATER_COLLECTOR, translate("Water Collector", "Collecteur d'eau"));
        addItem(ModItems.CARBON_FILTER, translate("Carbon Filter", "Filtre à charbon"));

        add("thirstexhaustion.container.water_collector", translate("Water Collector", "Collecteur d'eau"));
        add("thirstexhaustion.tooltip.waterskin", translate("Filter Uses : %s", "Utilisations du filtre : %s"));
    }

    private void addCleanWaterBottleTranslations() {
        add("item.thirstexhaustion.clean_water_bottle.effect.empty",
                translate("Uncraftable Clean Water Bottle", "Bouteille d'eau potable non craftable"));
        add("item.thirstexhaustion.clean_water_bottle.effect.water",
                translate("Clean Water Bottle", "Bouteille d'eau potable"));
        add("item.thirstexhaustion.clean_water_bottle.effect.awkward",
                translate("Awkward Bottle", "Bouteille bizarre"));
        add("item.thirstexhaustion.clean_water_bottle.effect.mundane",
                translate("Mundane Bottle", "Bouteille banale"));
        add("item.thirstexhaustion.clean_water_bottle.effect.thick",
                translate("Thick Bottle", "Bouteille épaisse"));
    }

    private void addConfiguration(ModConfigSpec.ConfigValue<?> configSpec, String name, String tooltip) {
        String key = "thirstexhaustion.configuration." + configSpec.getPath().getLast();
        add(key, name);
        add(key + ".tooltip", tooltip);
    }

    private void addSection(String section, String name, String tooltip) {
        String key = "thirstexhaustion.configuration." + section;
        add(key, name);
        add(key + ".tooltip", tooltip);
    }
}