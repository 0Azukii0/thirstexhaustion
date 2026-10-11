package com.azukii.thirstexhaustion.client.datagen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;
import net.neoforged.neoforge.client.textures.NamespacedDirectoryLister;

import java.util.concurrent.CompletableFuture;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ThirstExhaustion.MOD_ID);
    }

    @Override
    protected void gather() {
        SourceList gui = atlas(Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "gui"));
        gui.addSource(new NamespacedDirectoryLister(ThirstExhaustion.MOD_ID, "gui/sprites", ""));
    }
}
