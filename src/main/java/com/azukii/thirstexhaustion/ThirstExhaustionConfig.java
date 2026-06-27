package com.azukii.thirstexhaustion;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ThirstExhaustionConfig {
    public static final ThirstExhaustionConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

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

    public final ModConfigSpec.IntValue WATERSKIN_DURABILITY;
    public final ModConfigSpec.DoubleValue WATERSKIN_USE_COOLDOWN;
    public final ModConfigSpec.IntValue WATERSKIN_MAX_LEVEL;

    private ThirstExhaustionConfig(ModConfigSpec.Builder builder) {
        THIRST_LEVEL_MAX = builder.defineInRange("thirstLevelMax", 20, 1, 100);
        THIRST_LEVEL_MIN = builder.defineInRange("thirstLevelMin", 0, 0, 19);
        EXHAUSTION_MAX_LEVEL = builder.defineInRange("exhaustionMaxLevel", 4, 1, 10);
        EXHAUSTION_MIN_LEVEL_NORMAL = builder.defineInRange("exhaustionMinLevelNormal", 1, 0, 9);
        EXHAUSTION_MIN_LEVEL_HARDCORE = builder.defineInRange("exhaustionMinLevelHardcore", 0, 0, 9);

        CLEAN = builder.defineInRange("hungerChanceCleanWater", 0, 0, 100);
        DIRTY = builder.defineInRange("hungerChanceDirtyWater", 25, 0, 100);
        RIVER = builder.defineInRange("hungerChanceRiverWater", 10, 0, 100);
        OCEAN = builder.defineInRange("hungerChanceOceanWater", 100, 0, 100);
        FLOWING_WATER = builder.defineInRange("hungerChanceFlowingWater", 0, 0, 100);
        STILL_WATER = builder.defineInRange("hungerChanceStillWater", 50, 0, 100);

        WATERSKIN_DURABILITY = builder.defineInRange("waterskinDurability", 365, 1, 1000);
        WATERSKIN_USE_COOLDOWN = builder.defineInRange("waterskinUseCooldown", 3.0, 1.0, 60.0);
        WATERSKIN_MAX_LEVEL = builder.defineInRange("waterskinMaxLevel", 6, 1, 20);
    }

    static {
        Pair<ThirstExhaustionConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ThirstExhaustionConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
