package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class RectangleFrame extends AbstractFrame {
    private Color color;
    private Rectangle rectangle = new Rectangle();
    public RectangleFrame(int id, int layerIndex, Color color) {
        super(id, layerIndex);
        this.color = color;
    }

    @Override
    public void renderItself(Canvas canvas) {
        assert sprite != null : "The sprite is not set.";
        rectangle.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        canvas.renderRectangle(rectangle, color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
