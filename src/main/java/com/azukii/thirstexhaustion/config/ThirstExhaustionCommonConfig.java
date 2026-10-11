package com.azukii.thirstexhaustion.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ThirstExhaustionCommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue WATER_BUCKET_RECIPE = BUILDER.define("waterBucketRecipe", false);

    public static final ThirstExhaustionCommonConfig CONFIG = new ThirstExhaustionCommonConfig(BUILDER);
    public static final ModConfigSpec CONFIG_SPEC = BUILDER.build();

    public final ModConfigSpec.IntValue THIRST_LEVEL_MAX;
    public final ModConfigSpec.IntValue THIRST_LEVEL_MIN;
    public final ModConfigSpec.IntValue EXHAUSTION_MAX_LEVEL;
    public final ModConfigSpec.IntValue EXHAUSTION_MIN_LEVEL_NORMAL;
    public final ModConfigSpec.IntValue EXHAUSTION_MIN_LEVEL_HARDCORE;

    public final ModConfigSpec.IntValue CLEAN;
    public final ModConfigSpec.IntValue DIRTY;
    public final ModConfigSpec.IntValue RIVER;
    public final ModConfigSpec.IntValue OCEAN;
    public final ModConfigSpec.IntValue FLOWING_WATER;
    public final ModConfigSpec.IntValue STILL_WATER;

    private ThirstExhaustionCommonConfig(ModConfigSpec.Builder builder) {
        builder.push("thirst");
        THIRST_LEVEL_MAX = builder.defineInRange("thirstLevelMax", 20, 1, 100);
        THIRST_LEVEL_MIN = builder.defineInRange("thirstLevelMin", 0, 0, 19);
        builder.pop();

        builder.push("exhaustion");
        EXHAUSTION_MAX_LEVEL = builder.defineInRange("exhaustionMaxLevel", 4, 1, 10);
        EXHAUSTION_MIN_LEVEL_NORMAL = builder.defineInRange("exhaustionMinLevelNormal", 1, 0, 9);
        EXHAUSTION_MIN_LEVEL_HARDCORE = builder.defineInRange("exhaustionMinLevelHardcore", 0, 0, 9);
        builder.pop();

        builder.push("hungerChance");
        CLEAN = builder.defineInRange("hungerChanceCleanWater", 0, 0, 100);
        DIRTY = builder.defineInRange("hungerChanceDirtyWater", 25, 0, 100);
        RIVER = builder.defineInRange("hungerChanceRiverWater", 10, 0, 100);
        OCEAN = builder.defineInRange("hungerChanceOceanWater", 100, 0, 100);
        FLOWING_WATER = builder.defineInRange("hungerChanceFlowingWater", 0, 0, 100);
        STILL_WATER = builder.defineInRange("hungerChanceStillWater", 50, 0, 100);
        builder.pop();
    }
}
