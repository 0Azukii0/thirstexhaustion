package com.azukii.thirstexhaustion.attachment;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.component.ThirstData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ThirstExhaustion.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ThirstData>> THIRST_DATA =
            ATTACHMENT_TYPES.register("thirst_data", () -> AttachmentType.serializable(ThirstData::new)
                    .sync((_, _) -> true, ThirstData.STREAM_CODEC)
                    .build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
