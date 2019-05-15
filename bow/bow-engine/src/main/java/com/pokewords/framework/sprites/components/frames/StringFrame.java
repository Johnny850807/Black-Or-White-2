package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.*;

/**
 * A frame rendered as a text.
 * @author johnny850807 (waterball)
 */
public class StringFrame implements Frame {
    private Sprite sprite;
    private int id;
    private int layerIndex;
    private String text;

    public StringFrame(Sprite sprite, int id, String text) {
        this(sprite, id, 2, text);
    }

    public StringFrame(Sprite sprite, int id, int layerIndex, String text) {
        this.sprite = sprite;
        this.id = id;
        this.layerIndex = layerIndex;
        this.text = text;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getLayerIndex() {
        return layerIndex;
    }


    @Override
    public void renderItself(Canvas canvas) {
        canvas.renderText(sprite.getX(), sprite.getY(), text);
    }

    public void setText(String text) {
        this.text = text;
    }
}
