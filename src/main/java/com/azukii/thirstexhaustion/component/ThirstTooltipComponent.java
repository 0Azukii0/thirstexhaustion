package com.azukii.thirstexhaustion.component;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class ThirstTooltipComponent implements ClientTooltipComponent {
    private static final Identifier EMPTY = Identifier.fromNamespaceAndPath(ThirstExhaustion.MODID, "hud/thirst_empty");
    private static final Identifier FULL = Identifier.fromNamespaceAndPath(ThirstExhaustion.MODID, "hud/thirst_full");
    private static final Identifier FULL_DIRTY = Identifier.fromNamespaceAndPath(ThirstExhaustion.MODID, "hud/thirst_full_dirty");
    private static final Identifier HALF = Identifier.fromNamespaceAndPath(ThirstExhaustion.MODID, "hud/thirst_half");
    private static final Identifier HALF_DIRTY = Identifier.fromNamespaceAndPath(ThirstExhaustion.MODID, "hud/thirst_half_dirty");

    private final ThirstComponent component;

    public ThirstTooltipComponent(ThirstComponent component) {
        this.component = component;
    }

    @Override
    public int getHeight(Font font) {
        return 10;
    }

    @Override
    public int getWidth(Font font) {
        return component.level() * 9;
    }

    @Override
    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        int value = component.level();

        int full = value / 2;
        boolean hasHalf = (value % 2 != 0);
        int max = full + (hasHalf ? 1 : 0);

        boolean isCleanWaterContext = component.context() == WaterDrinkingContext.CLEAN || component.context() == WaterDrinkingContext.FLOWING_WATER;

        int xOffset = 0;
        for (int i = 0; i < max; i++) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EMPTY, x + xOffset, y, 9, 9);
            xOffset += 9;
        }
        xOffset = 0;
        for (int i = 0; i < full; i++) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, isCleanWaterContext ? FULL : FULL_DIRTY, x + xOffset, y, 9, 9);
            xOffset += 9;
        }
        if (hasHalf) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, isCleanWaterContext ? HALF : HALF_DIRTY, x + xOffset, y, 9, 9);
        }
    }
}
