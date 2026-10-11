package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.block.WaterCollectorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThirstExhaustion.MOD_ID);

    public static final DeferredBlock<Block> WATER_COLLECTOR = BLOCKS.registerBlock("water_collector", WaterCollectorBlock::new, p -> p
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.5F));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
