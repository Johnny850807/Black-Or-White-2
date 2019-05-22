package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * An EffectFrame created by wrapping a frame.
 * @author johnny850807 (waterball)
 */
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
    public void boundToSprite(@Nullable Sprite sprite) {
        super.boundToSprite(sprite);
        frame.boundToSprite(sprite);
    }

    @Override
    public void renderItself(Canvas canvas) {
        frame.renderItself(canvas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EffectWrappedFrame that = (EffectWrappedFrame) o;
        return duration == that.duration &&
                frame.equals(that.frame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration, frame);
    }

    @Override
    public EffectWrappedFrame clone() {
        EffectWrappedFrame clone = (EffectWrappedFrame) super.clone();
        clone.frame = this.frame.clone();
        return clone;
    }

    public Frame getWrappedFrame() {
        return frame;
    }
}
