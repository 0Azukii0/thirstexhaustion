package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.block.entity.WaterCollectorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ThirstExhaustion.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WaterCollectorBlockEntity>> WATER_RECUPERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("water_recuperator",
            () -> new BlockEntityType<>(WaterCollectorBlockEntity::new, ModBlocks.WATER_COLLECTOR.get()));

    public static void init(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
