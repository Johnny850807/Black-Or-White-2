package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RoundedRectangleFrame extends RectangleFrame {
    public final static int CANVAS_FLAG_FILLED = 1;
    private int arcWidth;
    private int arcHeight;

    public RoundedRectangleFrame(int layerIndex, Color color, int arcWidth, int arcHeight) {
        super(layerIndex, color);
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

    @Override
    protected void onDeserializing(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.onDeserializing(in);
        this.arcWidth = in.readInt();
        this.arcHeight = in.readInt();
    }

    @Override
    protected void onSerializing(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        super.onSerializing(out);
        out.writeInt(arcWidth);
        out.writeInt(arcHeight);
    }
}
