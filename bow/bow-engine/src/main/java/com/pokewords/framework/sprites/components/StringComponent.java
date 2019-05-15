package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A immutable renderable component that can be rendered as a text
 * @author johnny850807 (waterball)
 */
public class StringComponent extends Component implements Shareable, Renderable {
    public static final Font DEFAULT_FONT = new Font("微軟正黑體", Font.BOLD, 25);
    private String text;
    private Color color;
    private Font font;
    private boolean renderByCenter = false;
    private StringFrame stringFrame;
    private List<StringFrame> stringFrames;

    private Sprite sprite;

    public StringComponent() {
        this("", Color.black, DEFAULT_FONT, false);
    }

    public StringComponent(String text) {
        this(text, Color.black, DEFAULT_FONT, false);
    }

    public StringComponent(String text, boolean renderByCenter) {
        this(text, Color.black, DEFAULT_FONT, renderByCenter);
    }

    public StringComponent(String text, Color color, boolean renderByCenter) {
        this(text, color, DEFAULT_FONT, renderByCenter);
    }


    public StringComponent(String text, Color color, Font font, boolean renderByCenter) {
        this.renderByCenter = renderByCenter;
        this.text = text;
        this.color = color;
        this.font = font;
    }

    @Override
    public void onComponentInjected() {
        stringFrame = new StringFrame(sprite, 0, 2, text, color, font, renderByCenter);
        stringFrames = Collections.singletonList(stringFrame);
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return stringFrames;
    }

    @Override
    public List<StringFrame> getRenderedFrames() {
        return stringFrames;
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

    public List<StringFrame> getStringFrame() {
        return stringFrames;
    }

    public void setText(String text) {
        this.text = text;

        if (stringFrame != null)
            stringFrame.setText(text);
    }

    public void setColor(Color color) {
        this.color = color;

        if (stringFrame != null)
            stringFrame.setColor(color);
    }

    public void setFontSize(float sizePoint) {
        setFont(font.deriveFont(sizePoint));
    }

    public void setFont(Font font) {
        this.font = font;

        if (stringFrame != null)
            stringFrame.setFont(font);
    }

    public void setRenderByCenter(boolean renderByCenter) {
        this.renderByCenter = renderByCenter;

        if (stringFrame != null)
            stringFrame.setRenderByCenter(renderByCenter);
    }
}
