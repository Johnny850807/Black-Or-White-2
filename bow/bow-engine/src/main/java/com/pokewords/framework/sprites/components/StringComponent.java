package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.StringFrame;

import java.awt.*;

public class StringComponent extends FrameComponent<StringFrame> {
    public StringComponent(StringFrame frame) {
        super(frame);
    }

    public void setText(String text) {
        getFrame().text(text);
    }

    public void setColor(Color color) {
        getFrame().color(color);
    }

    public void setFont(Font font) {
        getFrame().font(font);
    }
}
