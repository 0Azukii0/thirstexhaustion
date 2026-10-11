package com.azukii.thirstexhaustion.core;

import com.azukii.thirstexhaustion.client.config.ThirstExhaustionCommonConfig;
import com.azukii.thirstexhaustion.registry.ModAttachments;
import com.mojang.logging.LogUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

public class ThirstData implements ValueIOSerializable {
    public static final Logger LOGGER = LogUtils.getLogger();
    private int exhaustionLevel = getExhaustionMaxLevel();
    private int thirstLevel = getThirstMaxLevel();
    private boolean initialized = false;

    public static final StreamCodec<RegistryFriendlyByteBuf, ThirstData> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public void encode(@NonNull RegistryFriendlyByteBuf buf, @NonNull ThirstData d) {
                    buf.writeVarInt(d.exhaustionLevel);
                    buf.writeVarInt(d.thirstLevel);
                    buf.writeBoolean(d.initialized);
                }

                @Override
                public @NonNull ThirstData decode(@NonNull RegistryFriendlyByteBuf buf) {
                    ThirstData thirst = new ThirstData();
                    thirst.exhaustionLevel = buf.readVarInt();
                    thirst.thirstLevel = buf.readVarInt();
                    thirst.initialized = buf.readBoolean();
                    return thirst;
                }
            };

    public boolean isInitialized() {
        return initialized;
    }

    private static int getExhaustionMaxLevel() {
        return ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MAX_LEVEL.getAsInt();
    }

    public int getExhaustionLevel() {
        return exhaustionLevel;
    }

    private static int getExhaustionMinLevelNormal() {
        return ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MIN_LEVEL_NORMAL.getAsInt();
    }

    private static int getExhaustionMinLevelHardcore() {
        return ThirstExhaustionCommonConfig.CONFIG.EXHAUSTION_MIN_LEVEL_HARDCORE.getAsInt();
    }

    private static int getThirstMaxLevel() {
        return ThirstExhaustionCommonConfig.CONFIG.THIRST_LEVEL_MAX.getAsInt();
    }

    private static int getThirstMinLevel() {
        return ThirstExhaustionCommonConfig.CONFIG.THIRST_LEVEL_MIN.getAsInt();
    }

    public boolean canDrink() {
        return this.thirstLevel < getThirstMaxLevel();
    }

    public int getExhaustionMinLevel(ServerPlayer player) {
        ServerLevel level = player.level();
        Difficulty difficulty = level.getDifficulty();
        return difficulty == Difficulty.HARD ? getExhaustionMinLevelHardcore() : getExhaustionMinLevelNormal();
    }

    public void addExhaustionLevel(ServerPlayer player, int amount) {
        this.exhaustionLevel = Math.clamp(this.exhaustionLevel + amount, getExhaustionMinLevel(player), getExhaustionMaxLevel());
    }

    public void removeExhaustionLevel(ServerPlayer player, int amount) {
        this.exhaustionLevel = Math.clamp(this.exhaustionLevel - amount, getExhaustionMinLevel(player), getExhaustionMaxLevel());
    }

    public void addThirstLevel(ServerPlayer player, int amount, WaterDrinkingContext context) {
        int oldThirst = this.thirstLevel;
        this.thirstLevel = Math.clamp(this.thirstLevel + amount, getThirstMinLevel(), getThirstMaxLevel());

        for (int threshold = 5; threshold <= 20; threshold += 5) {
            if (oldThirst < threshold && this.thirstLevel >= threshold && this.exhaustionLevel < threshold / 5 + getExhaustionMinLevel(player)) {
                addExhaustionLevel(player, 1);
            }
        }

        player.setData(ModAttachments.THIRST_DATA, this);
        addHungerEffect(player, context);

        LOGGER.debug("thirstLevel = {}", this.thirstLevel);
        LOGGER.debug("exhaustionLevel = {}", this.exhaustionLevel);
    }

    public void removeThirstLevel(ServerPlayer player, int amount) {
        this.thirstLevel = Math.clamp(this.thirstLevel - amount, getThirstMinLevel(), getThirstMaxLevel());
        if (this.thirstLevel == 15 || this.thirstLevel == 10 || this.thirstLevel == 5 || this.thirstLevel == 0) {
            removeExhaustionLevel(player, 1);
        }
    }

    private void addHungerEffect(ServerPlayer player, WaterDrinkingContext context) {
        if (context == WaterDrinkingContext.OCEAN) player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 2));

        int random = player.level().getRandom().nextInt(100);
        if (context == WaterDrinkingContext.RIVER && random > 90)
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
        if (context == WaterDrinkingContext.DIRTY && random > 75)
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0));
        if (context == WaterDrinkingContext.STILL_WATER && random > 50)
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 0));
    }

    public void ensureInitialized() {
        if (initialized) return;
        exhaustionLevel = getExhaustionMaxLevel();
        thirstLevel = getThirstMaxLevel();
        initialized = true;
    }

    @Override
    public void serialize(ValueOutput output) {
        output.putInt("exhaustionLevel", this.exhaustionLevel);
        output.putFloat("thirstLevel", this.thirstLevel);
        output.putBoolean("initialized", this.initialized);
    }

    @Override
    public void deserialize(ValueInput input) {
        this.exhaustionLevel = input.getIntOr("exhaustionLevel", getExhaustionMaxLevel());
        this.thirstLevel = input.getIntOr("thirstLevel", getThirstMaxLevel());
        this.initialized = input.getBooleanOr("initialized", false);
    }
}