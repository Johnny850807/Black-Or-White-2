package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.util.Objects;

/**
 * A frame rendered as a text.
 * <p>
 * Note that The width, and height of the text are determined by the font-areaSize at runtime rather than by sprite.
 * So you cannot get its width and height.
 *
 * @author johnny850807 (waterball)
 */
public class StringFrame extends AbstractFrame {
    protected String text;
    protected Color color;
    protected Font font;
    protected boolean renderByCenter;

    /**
     * if this is true, then the stringFrame will update the sprite's
     * area size fitting to the text every time its rendered.
     */
    protected boolean stickSpriteArea;

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
        Objects.requireNonNull(sprite);
        Dimension textDimension;

        if (renderByCenter)
            textDimension = canvas.renderTextWithCenterAdjusted(sprite.getX(), sprite.getY(), text, color, font);
        else
            textDimension = canvas.renderText(sprite.getX(), sprite.getY(), text, color, font);

        if (stickSpriteArea)
            sprite.setAreaSize(textDimension);
    }

    /**
     * update the sprite's area size fitting to the text every time its rendered.
     */
    public StringFrame stickSpriteSize() {
        stickSpriteArea = true;
        return this;
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
