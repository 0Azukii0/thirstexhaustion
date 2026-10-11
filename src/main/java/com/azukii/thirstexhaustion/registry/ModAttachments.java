package com.azukii.thirstexhaustion.registry;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.core.ThirstData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ThirstExhaustion.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ThirstData>> THIRST_DATA =
            ATTACHMENT_TYPES.register("thirst_data", () -> AttachmentType.serializable(ThirstData::new)
                    .sync((_, _) -> true, ThirstData.STREAM_CODEC)
                    .build());

    public static void init(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
