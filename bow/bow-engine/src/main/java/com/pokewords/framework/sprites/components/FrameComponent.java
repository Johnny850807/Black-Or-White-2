package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class FrameComponent<T extends Frame> extends CloneableComponent implements Renderable {
    private Sprite sprite;
    private T frame;
    private Collection<T> frameSingletonCollection;

    public FrameComponent(T frame) {
        setFrame(frame);
    }

    public void setFrame(T frame) {
        this.frame = frame;
        this.frameSingletonCollection = Collections.singleton(frame);
    }

    public T getFrame() {
        return frame;
    }

    @Override
    public void onComponentInjected() {
        frame.setSprite(sprite);
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return frameSingletonCollection;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return frameSingletonCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrameComponent<?> that = (FrameComponent<?>) o;
        return frame.equals(that.frame) &&
                frameSingletonCollection.equals(that.frameSingletonCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frame, frameSingletonCollection);
    }
}
