package com.azukii.thirstexhaustion.block.entity;

import com.azukii.thirstexhaustion.client.config.ThirstExhaustionStartupConfig;
import com.azukii.thirstexhaustion.component.WaterskinComponent;
import com.azukii.thirstexhaustion.gui.menu.WaterCollectorMenu;
import com.azukii.thirstexhaustion.recipe.WaterCollectionRecipe;
import com.azukii.thirstexhaustion.registry.ModBlockEntities;
import com.azukii.thirstexhaustion.registry.ModComponents;
import com.azukii.thirstexhaustion.registry.ModItems;
import com.azukii.thirstexhaustion.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class WaterCollectorBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private static final int[] SLOTS_SIDES = {1};
    private static final int[] SLOTS_DOWN = {2};
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int dataId) {
            return switch (dataId) {
                case 0 -> WaterCollectorBlockEntity.this.tickCount;
                case 1 -> WaterCollectorBlockEntity.this.fillProgress;
                case 2 -> WaterCollectorBlockEntity.this.fillTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int dataId, int value) {
            switch (dataId) {
                case 0:
                    WaterCollectorBlockEntity.this.tickCount = value;
                    break;
                case 1:
                    WaterCollectorBlockEntity.this.fillProgress = value;
                    break;
                case 2:
                    WaterCollectorBlockEntity.this.fillTotalTime = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int tickCount;
    private int fillProgress;
    private int fillTotalTime;

    public WaterCollectorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.WATER_RECUPERATOR_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, WaterCollectorBlockEntity entity) {
        boolean changed = false;
        ItemStack filter = entity.items.get(0);
        ItemStack input = entity.items.get(1);
        boolean isFiltered = !filter.isEmpty();
        boolean hasWater = level.isWaterAt(pos.above()) || level.isRainingAt(pos.above());

        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        Optional<RecipeHolder<WaterCollectionRecipe>> optional = level.recipeAccess()
                .recipeMap()
                .getRecipesFor(ModRecipes.WATER_COLLECTION_TYPE.get(), recipeInput, level)
                .filter(holder -> holder.value().matchesFilter(isFiltered))
                .findFirst();

        boolean working = false;
        if (hasWater && optional.isPresent()) {
            WaterCollectionRecipe recipe = optional.get().value();
            ItemStack output = recipe.assemble(recipeInput);
            entity.fillTotalTime = recipe.fillingTime();

            int filterCost = 1;
            boolean canProduce = true;

            if (isFiltered && output.is(ModItems.WATERSKIN)) {
                int max = ThirstExhaustionStartupConfig.CONFIG.WATERSKIN_MAX_LEVEL.getAsInt();
                int remaining = filter.getMaxDamage() - filter.getDamageValue();
                canProduce = filter.isDamageableItem() && remaining >= max;
                if (canProduce) {
                    applyWaterskinLevel(output, max, remaining);
                    filterCost = max;
                }
            }

            if (canProduce && canFill(entity.items, entity.getMaxStackSize(), output)) {
                working = true;
                entity.tickCount++;
                entity.fillProgress++;
                if (entity.fillProgress >= entity.fillTotalTime) {
                    fill(entity.items, input, output);
                    if (isFiltered) filter.hurtAndBreak(filterCost, level, null, _ -> {
                    });
                    entity.fillProgress = 0;
                    changed = true;
                }
            }
        }

        if (!working && entity.fillProgress > 0) {
            entity.fillProgress = Mth.clamp(entity.fillProgress - 2, 0, 100);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private static boolean canFill(NonNullList<ItemStack> items, int maxStackSize, ItemStack fillResult) {
        ItemStack resultItemStack = items.get(2);
        if (resultItemStack.isEmpty()) {
            return true;
        } else if (!ItemStack.isSameItemSameComponents(resultItemStack, fillResult)) {
            return false;
        } else {
            int resultCount = resultItemStack.getCount() + fillResult.count();
            int maxResultCount = Math.min(maxStackSize, fillResult.getMaxStackSize());
            return resultCount <= maxResultCount;
        }
    }

    private static void fill(NonNullList<ItemStack> items, ItemStack inputItemStack, ItemStack result) {
        ItemStack resultItemStack = items.get(2);
        if (resultItemStack.isEmpty()) {
            items.set(2, result.copy());
        } else {
            resultItemStack.grow(result.getCount());
        }
        inputItemStack.shrink(1);
    }

    private static void applyWaterskinLevel(ItemStack output, int max, int filterRemaining) {
        output.applyComponents(DataComponentPatch.builder()
                .set(ModComponents.WATERSKIN.get(), new WaterskinComponent(max, max, filterRemaining - max))
                .build());
    }

    @Override
    protected @NonNull Component getDefaultName() {
        return Component.translatable("thirstexhaustion.container.water_collector");
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.tickCount = input.getIntOr("tickCount", 0);
        this.fillProgress = input.getIntOr("fillProgress", 0);
        this.fillTotalTime = input.getIntOr("fillTotalTime", 0);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("tickCount", this.tickCount);
        output.putInt("fillProgress", this.fillProgress);
        output.putInt("fillTotalTime", this.fillTotalTime);
        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    protected @NonNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NonNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected @NonNull AbstractContainerMenu createMenu(int containerId, @NonNull Inventory inventory) {
        return new WaterCollectorMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public int @NonNull [] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_DOWN;
        } else if (direction != Direction.UP) return SLOTS_SIDES;
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction) {
        return direction != Direction.UP && this.canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return direction != Direction.UP;
    }
}
