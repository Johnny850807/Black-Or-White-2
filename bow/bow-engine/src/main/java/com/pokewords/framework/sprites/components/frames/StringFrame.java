package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class StringFrame implements Frame {
    private Sprite sprite;
    private int layerIndex;
    private String text;

    public StringFrame(String text) {
        this(2, text);
    }

    public StringFrame(int layerIndex, String text) {
        this.layerIndex = layerIndex;
        this.text = text;
    }

    @Override
    public int getLayerIndex() {
        return layerIndex;
    }

    @Override
    public void renderItself(Canvas canvas) {
        canvas.renderText(sprite.getX(), sprite.getY(), text);
    }
}
