package com.azukii.thirstexhaustion.core;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.component.ThirstComponent;
import com.azukii.thirstexhaustion.component.WaterskinComponent;
import com.azukii.thirstexhaustion.registry.ModAttachments;
import com.azukii.thirstexhaustion.registry.ModComponents;
import com.azukii.thirstexhaustion.registry.ModItemTags;
import com.azukii.thirstexhaustion.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.WeatherData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.PotionBrewEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EventBusSubscriber(modid = ThirstExhaustion.MOD_ID)
public class ThirstEvents {

    private static final Map<Integer, ThirstComponent> pendingThirst = new HashMap<>();

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack filter = ItemStack.EMPTY;
        ItemStack waterskin = ItemStack.EMPTY;
        for (ItemStack inputStack : event.getInventory()) {
            if (inputStack.is(ModItemTags.WATERSKIN_FILTERS)) {
                filter = inputStack;
            } else if (inputStack.is(ModItems.WATERSKIN) && inputStack.has(ModComponents.WATERSKIN)) {
                waterskin = inputStack;
            }
        }

        if (waterskin.has(ModComponents.WATERSKIN)) {
            DataComponentPatch waterskinPatch = DataComponentPatch.builder().set(ModComponents.WATERSKIN.get(),
                    new WaterskinComponent(
                            waterskin.get(ModComponents.WATERSKIN).thirstLevel(),
                            waterskin.get(ModComponents.WATERSKIN).thirstMaxLevel(),
                            filter.isDamageableItem() ? filter.getMaxDamage() - filter.getDamageValue() : filter.getMaxDamage()
                    )
            ).build();
            event.getCrafting().applyComponents(waterskinPatch);
        }
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
            if (!data.canDrink() || waterskin.thirstLevel() == 0) {
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
