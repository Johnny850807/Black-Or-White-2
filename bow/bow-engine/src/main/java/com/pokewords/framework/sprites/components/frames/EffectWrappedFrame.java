package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * An EffectFrame created by wrapping a frame.
 * @author johnny850807 (waterball)
 */
public class EffectWrappedFrame extends DefaultEffectFrame {
    private SerializableFrame frame;

    public EffectWrappedFrame(SerializableFrame frame, int id, int duration) {
        super(id, frame.getLayerIndex(), duration);
        setFlags(frame.getFlags());
        if (frame.getSprite() == null)
        {
            setPosition(new Point(frame.getX(), frame.getY()));
            setSize(new Dimension(frame.getWidth(), frame.getHeight()));
        }
        else
            boundToSprite(frame.getSprite());
        this.frame = frame;
    }

    @Override
    public void setLayerIndex(int layerIndex) {
        super.setLayerIndex(layerIndex);
        frame.setLayerIndex(layerIndex);
    }

    @Override
    public void setFlags(int flags) {
        super.setFlags(flags);
        frame.setFlags(flags);
    }

    @Override
    public void boundToSprite(@Nullable Sprite sprite) {
        super.boundToSprite(sprite);
        frame.boundToSprite(sprite);
    }


    @Override
    public void setPosition(Point position) {
        super.setPosition(position);
        frame.setPosition(position);
    }

    @Override
    public void setSize(Dimension dimension) {
        super.setSize(dimension);
        frame.setSize(dimension);
    }

    @Override
    public int getX() {
        assert frame.getX() == this.getX();
        return getX();
    }

    @Override
    public int getY() {
        assert frame.getY() == this.getY();
        return getY();
    }

    @Override
    public int getWidth() {
        assert frame.getWidth() == this.getWidth();
        return getWidth();
    }

    @Override
    public int getHeight() {
        assert frame.getHeight() == this.getHeight();
        return getHeight();
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
        return frame.equals(that.frame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), frame);
    }

    @Override
    public EffectWrappedFrame clone() {
        EffectWrappedFrame clone = (EffectWrappedFrame) super.clone();
        clone.frame = this.frame.clone();
        return clone;
    }

    @Override
    protected void onDeserializing(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.frame = (SerializableFrame) in.readObject();
    }

    @Override
    protected void onSerializing(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject(frame);
    }
}
