package com.azukii.thirstexhaustion;

import com.azukii.thirstexhaustion.attachment.ModAttachments;
import com.azukii.thirstexhaustion.component.*;
import com.azukii.thirstexhaustion.data.ModDataMapProvider;
import com.azukii.thirstexhaustion.data.ModDataMaps;
import com.azukii.thirstexhaustion.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.WeatherData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.brewing.PotionBrewEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mod(ThirstExhaustion.MODID)
@EventBusSubscriber(modid = ThirstExhaustion.MODID)
public class ThirstExhaustion {
    public static final String MODID = "thirstexhaustion";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThirstExhaustion(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerConfig(ModConfig.Type.STARTUP, ThirstExhaustionConfig.CONFIG_SPEC);

        ModAttachments.register(modEventBus);
        ModComponents.register(modEventBus);
        ModItems.register(modEventBus);
        LOGGER.info("Thirst Exhaustion System Loaded");
    }

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ModDataMaps.THIRST_REGISTRY_DATA);
        System.out.println(ModDataMapProvider.getHolder(Items.GOLDEN_APPLE).getData(ModDataMaps.THIRST_REGISTRY_DATA));
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.insertAfter(Items.SPIDER_EYE.getDefaultInstance(), ModItems.CLEAN_WATER_BOWL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.SPIDER_EYE.getDefaultInstance(), ModItems.WATER_BOWL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.POTION.getDefaultInstance(), ModItems.WATERSKIN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.POTION.getDefaultInstance(), ModItems.CLEAN_WATER_BOTTLE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SubscribeEvent
    public static void modifyDefaultComponentsEvent(ModifyDefaultComponentsEvent event) {

        event.modifyMatching(
                ((_, components) -> components.has(DataComponents.POTION_CONTENTS)),
                (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 3));
        event.modify(Items.MUSHROOM_STEW, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modify(Items.BEETROOT_SOUP, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modify(Items.RABBIT_STEW, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modify(Items.SUSPICIOUS_STEW, (components, _, _) -> components.set(DataComponents.MAX_STACK_SIZE, 16));
    }


    @SubscribeEvent
    public static void onDrinkConsumed(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack stack = event.getItem();
        if (stack.isEmpty()) return;

        ThirstComponent thirst = ThirstComponent.get(event.getItem());
        if (thirst == null) return;

        ThirstData data = player.getData(ModAttachments.THIRST_DATA);
        data.addThirstLevel(player, thirst.level(), thirst.context());
    }

    @SubscribeEvent
    public static void onFinishUse(LivingEntityUseItemEvent.Finish event) {
        ItemStack original = event.getItem();
        ItemStack result = event.getResultStack();
        if (!(event.getEntity() instanceof Player player)) return;
        Level level = player.level();
        BlockPos pos = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.WATER).getBlockPos();

        if (original.is(Items.GLASS_BOTTLE) && result.is(Items.POTION)) {
            int thirstLevel = Objects.requireNonNull(ThirstComponent.get(Items.POTION.getDefaultInstance())).level();
            WaterDrinkingContext context = ThirstComponent.getWaterDrinkingContextFromBlockHitResult(level, pos);
            result.set(ModComponents.THIRST.get(), new ThirstComponent(thirstLevel, context));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ThirstData thirst = player.getData(ModAttachments.THIRST_DATA);

        if (!thirst.isInitialized()) {
            thirst.ensureInitialized();
            player.setData(ModAttachments.THIRST_DATA, thirst);
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        ThirstData data = player.getData(ModAttachments.THIRST_DATA);
        ThirstComponent thirst = ThirstComponent.get(stack);
        WaterskinComponent waterskin = stack.get(ModComponents.WATERSKIN);

        if (thirst != null) {
            if (!data.canDrink() && !stack.has(DataComponents.FOOD)) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
        } else if (waterskin != null) {
            if (!data.canDrink() || waterskin.level() == 0) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickItemOnWater(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide()) return;

        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack itemStack = event.getItemStack();

        if (itemStack.is(Items.BOWL)) {
            BlockPos pos = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.WATER).getBlockPos();
            if (level.getFluidState(pos).is(FluidTags.WATER)) {
                WaterDrinkingContext context = ThirstComponent.getWaterDrinkingContextFromBlockHitResult(level, pos);
                int thirstLevel = Objects.requireNonNull(ThirstComponent.get(ModItems.WATER_BOWL.toStack())).level();

                DataComponentPatch patch = DataComponentPatch.builder().set(ModComponents.THIRST.get(), new ThirstComponent(thirstLevel, context)).build();
                ItemStack newStack = new ItemStackTemplate(ModItems.WATER_BOWL, patch).create();

                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    player.setItemInHand(event.getHand(), newStack);
                } else {
                    if (!player.getInventory().add(newStack)) {
                        player.drop(newStack, false);
                    }
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ServerLevel level = player.level();

        WeatherData weather = level.getServer().getWeatherData();
        if (weather.isRaining() || weather.isThundering()) {
            BlockPos headPos = player.blockPosition().above();
            if (level.isEmptyBlock(headPos)) {
                ThirstData thirst = player.getData(ModAttachments.THIRST_DATA);
                thirst.addThirstLevel(player, 1, WaterDrinkingContext.CLEAN);
                return;
            }
        }

        ThirstData thirst = player.getData(ModAttachments.THIRST_DATA);
        thirst.addThirstLevel(player, 1, ThirstComponent.getWaterDrinkingContextFromBlockHitResult(level, event.getPos()));
    }

    private static final Map<Integer, ThirstComponent> pendingThirst = new HashMap<>();

    @SubscribeEvent
    public static void onBrewingPre(PotionBrewEvent.Pre event) {
        pendingThirst.clear();
        for (int i = 0; i < 3; i++) {
            ThirstComponent thirst = ThirstComponent.get(event.getItem(i));
            if (thirst != null) pendingThirst.put(i, thirst);
        }
    }

    @SubscribeEvent
    public static void onBrewingPost(PotionBrewEvent.Post event) {
        for (var entry : pendingThirst.entrySet()) {
            int i = entry.getKey();
            ThirstComponent thirst = entry.getValue();
            ItemStack stack = event.getItem(i);
            if (stack.isEmpty()) continue;

            ItemStack copy = stack.copy();
            if (thirst.level() > 0) {
                copy.set(ModComponents.THIRST.get(), ThirstComponent.of(thirst.level() - 1));
            } else {
                copy.remove(ModComponents.THIRST.get());
            }
            event.setItem(i, copy);
        }
        pendingThirst.clear();
    }
}
