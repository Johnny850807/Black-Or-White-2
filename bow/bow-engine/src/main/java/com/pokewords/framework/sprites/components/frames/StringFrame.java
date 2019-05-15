package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;

import java.awt.*;

/**
 * A frame rendered as a text.
 * @author johnny850807 (waterball)
 */
public class StringFrame implements Frame {
    private Sprite sprite;
    private int id;
    private int layerIndex;
    private String text;
    private Color color;
    private Font font;
    private boolean renderByCenter;

    public StringFrame(Sprite sprite, int id, int layerIndex, String text, Color color, Font font, boolean renderByCenter) {
        this.color = color;
        this.sprite = sprite;
        this.id = id;
        this.layerIndex = layerIndex;
        this.text = text;
        this.font = font;
        this.renderByCenter = renderByCenter;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getLayerIndex() {
        return layerIndex;
    }


    @Override
    public void renderItself(Canvas canvas) {
        if (renderByCenter)
            canvas.renderTextWithCenterAdjusted(sprite.getX(), sprite.getY(), text, color, font);
        else
            canvas.renderText(sprite.getX(), sprite.getY(), text, color, font);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setRenderByCenter(boolean renderByCenter) {
        this.renderByCenter = renderByCenter;
    }
}
