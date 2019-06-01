package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RectangleFrame extends SerializableFrame {
    public final static int CANVAS_FLAG_FILLED = 1;
    private Color color;

    public RectangleFrame(int layerIndex, Color color) {
        super(layerIndex);
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
    protected void onDeserializing(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.color = new Color(in.readInt());
    }

    @Override
    protected void onSerializing(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeInt(color.getRGB());
    }

    @Override
    public RectangleFrame clone() {
        RectangleFrame clone = (RectangleFrame) super.clone();
        clone.color = new Color(color.getRGB());
        return clone;
    }
}
