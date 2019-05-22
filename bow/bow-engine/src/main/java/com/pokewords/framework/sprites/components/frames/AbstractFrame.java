package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

public abstract class AbstractFrame implements Frame {
    private Rectangle area = new Rectangle();
    protected @Nullable Sprite sprite;
    protected int id;
    protected int layerIndex;
    protected int flags;


    public AbstractFrame(int id, int layerIndex) {
        this.id = id;
        this.layerIndex = layerIndex;
    }

    public AbstractFrame(int id, int layerIndex, int flags) {
        this.id = id;
        this.layerIndex = layerIndex;
        this.flags = flags;
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
    public void boundToSprite(@Nullable Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public int getFlags() {
        return flags;
    }

    public void setPosition(Point position) {
        if (getSprite() != null)
            throw new IllegalStateException("The frame is bound to a Sprite, you should set that sprite's position instead.");
        area.setLocation(position);
    }

    public void setSize(Dimension dimension) {
        if (getSprite() != null)
            throw new IllegalStateException("The frame is bound to a Sprite, you should set that sprite's size instead.");
        area.setSize(dimension);
    }

    @Override
    public int getX() {
        if (getSprite() != null)
            return getSprite().getX();
        return (int) area.getX();
    }

    @Override
    public int getY() {
        if (getSprite() != null)
            return getSprite().getY();
        return (int) area.getY();
    }

    @Override
    public int getWidth() {
        if (getSprite() != null)
            return getSprite().getWidth();
        return (int) area.getWidth();
    }

    public int getHeight() {
        if (getSprite() != null)
            return getSprite().getHeight();
        return (int) area.getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFrame that = (AbstractFrame) o;
        return id == that.id &&
                layerIndex == that.layerIndex &&
                flags == that.flags;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, layerIndex, flags);
    }

    @Override
    public Frame clone() {
        try {
            return (Frame) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

}
