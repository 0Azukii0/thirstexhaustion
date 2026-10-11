package com.azukii.thirstexhaustion.component;

import com.azukii.thirstexhaustion.ThirstExhaustion;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;

public class WaterskinTooltipComponent implements ClientTooltipComponent {
    private static final Identifier EMPTY = Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "hud/thirst_empty");
    private static final Identifier FULL = Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "hud/thirst_full");
    private static final Identifier HALF = Identifier.fromNamespaceAndPath(ThirstExhaustion.MOD_ID, "hud/thirst_half");

    private final WaterskinComponent component;

    public WaterskinTooltipComponent(WaterskinComponent component) {
        this.component = component;
    }

    @Override
    public int getHeight(Font font) {
        return 20;
    }

    @Override
    public int getWidth(Font font) {
        return component.thirstLevel() * 9;
    }

    @Override
    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        graphics.text(font, Component.translatable("thirstexhaustion.tooltip.waterskin", component.filterLevel()), x, y, CommonColors.GRAY);

        int value = component.thirstLevel();
        int maxLevel = component.thirstMaxLevel() / 2;

        int full = value / 2;
        boolean hasHalf = (value % 2 != 0);

        int xOffset = 0;
        int yOffset = 10;
        for (int i = 0; i < maxLevel; i++) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EMPTY, x + xOffset, y + yOffset, 9, 9);
            xOffset += 9;
        }
        xOffset = 0;
        for (int i = 0; i < full; i++) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, FULL, x + xOffset, y + yOffset, 9, 9);
            xOffset += 9;
        }
        if (hasHalf) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HALF, x + xOffset, y + yOffset, 9, 9);
        }
    }
}
