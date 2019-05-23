package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class RectangleFrame extends AbstractFrame {
    public final static int CANVAS_FLAG_FILLED = 1;
    private Color color;

    public RectangleFrame(int id, int layerIndex, Color color) {
        super(id, layerIndex);
        this.color = color;
    }

    public RectangleFrame flags(int flags) {
        this.flags = flags;
        return this;
    }

    @Override
    public void renderItself(Canvas canvas) {
        canvas.render(this);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public RectangleFrame clone() {
        RectangleFrame clone = (RectangleFrame) super.clone();
        clone.color = new Color(color.getRGB());
        return clone;
    }
}
