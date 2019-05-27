package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

/**
 * @author johnny850807 (waterball)
 */
public abstract class AbstractFrame implements Frame {
    protected Rectangle area = new Rectangle();
    protected int layerIndex;
    protected int flags;

    protected @Nullable Sprite sprite;

    public AbstractFrame(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getLayerIndex() {
        return layerIndex;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    /**
     * @inheritDoc
     */
    @Nullable
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void boundToSprite(@Nullable Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFlags(int flags) {
        this.flags = flags;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getFlags() {
        return flags;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPosition(Point position) {
        if (getSprite() != null)
            throw new IllegalStateException("The frame is bound to a Sprite, you should set that sprite's position instead.");
        area.setLocation(position);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSize(Dimension dimension) {
        if (getSprite() != null)
            throw new IllegalStateException("The frame is bound to a Sprite, you should set that sprite's size instead.");
        area.setSize(dimension);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getX() {
        if (getSprite() != null)
            return getSprite().getX();
        return (int) area.getX();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getY() {
        if (getSprite() != null)
            return getSprite().getY();
        return (int) area.getY();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getWidth() {
        if (getSprite() != null)
            return getSprite().getWidth();
        return (int) area.getWidth();
    }

    /**
     * @inheritDoc
     */
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
        return layerIndex == that.layerIndex &&
                flags == that.flags  &&
                area.equals(that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, layerIndex, flags);
    }

    @Override
    public AbstractFrame clone() {
        try {
            AbstractFrame clone = (AbstractFrame) super.clone();
            clone.area = (Rectangle) this.area.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

}
