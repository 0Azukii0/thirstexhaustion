package com.azukii.thirstexhaustion.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ThirstExhaustionStartupConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ThirstExhaustionStartupConfig CONFIG = new ThirstExhaustionStartupConfig(BUILDER);
    public static final ModConfigSpec CONFIG_SPEC = BUILDER.build();

    public final ModConfigSpec.IntValue WATERSKIN_DURABILITY;
    public final ModConfigSpec.IntValue WATERSKIN_MAX_LEVEL;

    private ThirstExhaustionStartupConfig(ModConfigSpec.Builder builder) {
        builder.push("waterskin");
        WATERSKIN_DURABILITY = builder.defineInRange("waterskinDurability", 365, 1, 1000);
        WATERSKIN_MAX_LEVEL = builder.defineInRange("waterskinMaxLevel", 6, 1, 20);
        builder.pop();
    }
}
