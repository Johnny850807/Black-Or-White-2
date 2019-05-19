package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

public class EffectWrappedFrame extends DefaultEffectFrame {
    private int duration;
    private Frame frame;

    public EffectWrappedFrame(Frame frame, int duration) {
        super(frame.getId(), frame.getLayerIndex());
        this.duration = duration;
        this.frame = frame;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setLayerIndex(int layerIndex) {
        super.setLayerIndex(layerIndex);
        frame.setLayerIndex(layerIndex);
    }

    @Override
    public void setSprite(@Nullable Sprite sprite) {
        super.setSprite(sprite);
        frame.setSprite(sprite);
    }

    @Override
    public void renderItself(Canvas canvas) {
        frame.renderItself(canvas);
    }
}
