package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.StringFrame;

import java.awt.*;

public class StringComponent extends FrameComponent<StringFrame> {
    public StringComponent(StringFrame frame) {
        super(frame);
    }

    public void setText(String text) {
        getFrame().setText(text);
    }

    public void setColor(Color color) {
        getFrame().setColor(color);
    }

    public void setFont(Font font) {
        getFrame().setFont(font);
    }
}
