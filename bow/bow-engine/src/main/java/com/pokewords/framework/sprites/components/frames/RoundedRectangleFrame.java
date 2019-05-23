package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class RoundedRectangleFrame extends RectangleFrame {
    public final static int CANVAS_FLAG_FILLED = 1;
    private int arcWidth;
    private int arcHeight;

    public RoundedRectangleFrame(int id, int layerIndex, Color color, int arcWidth, int arcHeight) {
        super(id, layerIndex, color);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    public RoundedRectangleFrame flags(int flags) {
        this.flags = flags;
        return this;
    }

    @Override
    public void renderItself(Canvas canvas) {
        canvas.render(this);
    }

    public int getArcWidth() {
        return arcWidth;
    }

    public int getArcHeight() {
        return arcHeight;
    }

}
