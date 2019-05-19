package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractFrame implements Frame {
    protected @Nullable Sprite sprite;
    protected int id;
    protected int layerIndex;

    public AbstractFrame(int id, int layerIndex) {
        this.id = id;
        this.layerIndex = layerIndex;
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
    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    @Nullable
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void setSprite(@Nullable Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFrame that = (AbstractFrame) o;
        return id == that.id &&
                layerIndex == that.layerIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, layerIndex);
    }
}
