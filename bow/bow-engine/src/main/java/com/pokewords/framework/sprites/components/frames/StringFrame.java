package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.GameEffect;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class StringFrame implements Frame {
    private int x;
    private int y;
    private String text;

    public StringFrame(Point position, String text) {
        this(position.x, position.y, text);
    }

    public StringFrame(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public int getLayerIndex() {
        return 2;
    }

    @Override
    public final void apply(AppStateWorld gameWorld, Sprite sprite) { }

    @Override
    public final void addEffect(GameEffect effect) { }

    @Override
    public void renderItself(Canvas canvas) {
        canvas.renderText(x, y, text);
    }
}
