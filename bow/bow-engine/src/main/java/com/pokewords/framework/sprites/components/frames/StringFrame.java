package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.util.Objects;

/**
 * A frame rendered as a text.
 * @author johnny850807 (waterball)
 */
public class StringFrame extends AbstractFrame implements Frame {
    protected String text;
    protected Color color;
    protected Font font;
    protected boolean renderByCenter;

    public StringFrame(int id, int layerIndex, String text, boolean renderByCenter) {
        this(id, layerIndex, text, Color.black, new Font("微軟正黑體", Font.PLAIN, 15), renderByCenter);
    }

    public StringFrame(int id, int layerIndex, String text, Color color, boolean renderByCenter) {
        this(id, layerIndex, text, color, new Font("微軟正黑體", Font.PLAIN, 15), renderByCenter);
    }

    public StringFrame(int id, int layerIndex, String text, Font font, boolean renderByCenter) {
        this(id, layerIndex, text, Color.black, font, renderByCenter);
    }

    public StringFrame(int id, int layerIndex, String text, Color color, Font font, boolean renderByCenter) {
        super(id, layerIndex);
        this.text = text;
        this.color = color;
        this.font = font;
        this.renderByCenter = renderByCenter;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setRenderByCenter(boolean renderByCenter) {
        this.renderByCenter = renderByCenter;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public boolean isRenderByCenter() {
        return renderByCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringFrame that = (StringFrame) o;
        return renderByCenter == that.renderByCenter &&
                text.equals(that.text) &&
                color.equals(that.color) &&
                font.equals(that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, color, font, renderByCenter);
    }
}
