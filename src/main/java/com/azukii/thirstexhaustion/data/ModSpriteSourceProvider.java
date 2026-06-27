package com.azukii.thirstexhaustion.data;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;
import net.neoforged.neoforge.client.textures.NamespacedDirectoryLister;

import java.util.concurrent.CompletableFuture;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ThirstExhaustion.MODID);
    }

    @Override
    protected void gather() {
        SourceList gui = atlas(Identifier.fromNamespaceAndPath(ThirstExhaustion.MODID, "gui"));
        gui.addSource(new NamespacedDirectoryLister(ThirstExhaustion.MODID, "gui/sprites", ""));
    }
}
