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
    /**
     * the stringFrame's rendered point will be seen as its center point.
     */
    public final static int CANVAS_FLAG_RENDER_BY_CENTER = 1;

    /**
     * the stringFrame will update the sprite's
     * area size fitting to the text every time its rendered.
     */
    public final static int FLAG_STICK_SPRITE_AREA = 1 << 1;

    protected String text;
    protected Color color = Color.black;
    protected Font font = new Font("微軟正黑體", Font.PLAIN, 15);


    public StringFrame(int id, int layerIndex, String text) {
        super(id, layerIndex);
        this.text = text;
    }

    public StringFrame(int id, int layerIndex, String text, int flags) {
        super(id, layerIndex, flags);
        this.text = text;
    }

    @Override
    public void renderItself(Canvas canvas) {
        Objects.requireNonNull(sprite);
        Dimension textDimension = canvas.render(this);

        if (hasFlag(FLAG_STICK_SPRITE_AREA))
            sprite.setAreaSize(textDimension);
    }

    public StringFrame text(String text) {
        this.text = text;
        return this;
    }

    public StringFrame flags(int flags) {
        this.flags = flags;
        return this;
    }

    public StringFrame color(Color color) {
        this.color = color;
        return this;
    }

    public StringFrame font(Font font) {
        this.font = font;
        return this;
    }

    public StringFrame fontStyle(int style) {
        this.font = this.font.deriveFont(style);
        return this;
    }

    public StringFrame fontSize(float pt) {
        this.font = this.font.deriveFont(pt);
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringFrame that = (StringFrame) o;
        return text.equals(that.text) &&
                color.equals(that.color) &&
                font.equals(that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, color, font);
    }
}
