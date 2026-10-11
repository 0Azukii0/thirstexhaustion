package com.azukii.thirstexhaustion.client.screen;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import com.azukii.thirstexhaustion.gui.menu.WaterCollectorMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class WaterCollectorScreen extends AbstractContainerScreen<WaterCollectorMenu> {
    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "textures/gui/container/water_recuperator.png");
    private static final Identifier BUBBLES_SPRITE = Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "container/water_recuperator/drops");
    private static final Identifier FILL_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "container/water_recuperator/fill_progress");
    private static final int[] DROPS_LENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public WaterCollectorScreen(WaterCollectorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        int tickCount = this.menu.getTickCount();
        if (tickCount > 0) {
            int length = DROPS_LENGTHS[tickCount / 2 % 7];
            if (length > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUBBLES_SPRITE, 12, 29, 0, 29 - length, xo + 57, yo + 22 + 29 - length, 12, length);
            }
        }
        int fillProgressWidth = Mth.ceil(Mth.clamp(this.menu.getFillProgress() / (float) this.menu.getFillTotalTime(), 0.0F, 1.0F) * 24.0F);
        if (fillProgressWidth > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, FILL_PROGRESS_SPRITE, 24, 16, 0, 0, xo + 79, yo + 34, fillProgressWidth, 16);
        }
    }
}
