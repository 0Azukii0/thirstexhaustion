package com.azukii.thirstexhaustion.gui.menu;

import com.azukii.thirstexhaustion.registry.ModItemTags;
import com.azukii.thirstexhaustion.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class WaterCollectorMenu extends AbstractContainerMenu {
    public static final int CONTAINER_SIZE = 3;
    public static final int CONTAINER_DATA_SIZE = 3;
    protected final Container container;
    protected final ContainerData data;

    public WaterCollectorMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(CONTAINER_SIZE), new SimpleContainerData(CONTAINER_DATA_SIZE));
    }

    public WaterCollectorMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ModMenus.WATER_RECUPERATOR_MENU.get(), containerId);
        this.container = container;
        this.data = data;
        this.addSlot(new Slot(container, 0, 21, 34) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(ModItemTags.WATER_COLLECTOR_FILTERS);
            }
        });
        this.addSlot(new Slot(container, 1, 56, 53));
        this.addSlot(new Slot(container, 2, 116, 35) {
            @Override
            public boolean mayPlace(@NonNull ItemStack itemStack) {
                return false;
            }
        });
        this.addStandardInventorySlots(playerInventory, 8, 84);
        this.addDataSlots(data);
    }

    public int getTickCount() {
        return data.get(0);
    }

    public int getFillProgress() {
        return data.get(1);
    }

    public int getFillTotalTime() {
        return data.get(2);
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            copy = stack.copy();

            if (index < CONTAINER_SIZE) {
                if (!this.moveItemStackTo(stack, CONTAINER_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(stack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return copy;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return this.container.stillValid(player);
    }
}
