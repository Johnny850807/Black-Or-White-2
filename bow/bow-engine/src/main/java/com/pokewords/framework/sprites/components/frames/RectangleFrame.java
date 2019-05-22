package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class RectangleFrame extends AbstractFrame {
    private Color color;

    public RectangleFrame(int id, int layerIndex, Color color) {
        super(id, layerIndex);
        this.color = color;
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
}
