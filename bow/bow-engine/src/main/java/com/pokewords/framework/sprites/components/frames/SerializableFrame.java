package com.pokewords.framework.sprites.components.frames;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The frame that can be serialized.
 * If you are going to support networking game, then all of your frames should inherit from SerializableFrame.
 *
 * Note: 1. The order of reading and writing to your objects or primitive variables should be the same.
 * 2. You don't have to make those non-serializable objects 'transient' because the default serialization's
 * reading and writing are not used, all of the serialization / deserialization process is determined by
 * your onSerializing() / onDeserializing() implementation. However,
 * if you write a non-serializable object, the error still happens.
 * @author johnny850807 (waterball)
 */
public abstract class SerializableFrame extends AbstractFrame {
    private static final long serialVersionUID = 1L;

    public SerializableFrame(int layerIndex) {
        super(layerIndex);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.layerIndex = in.readInt();
        this.flags = in.readInt();
        this.area = (Rectangle) in.readObject();

        onDeserializing(in);
    }


    protected abstract void onDeserializing(ObjectInputStream in) throws IOException, ClassNotFoundException;

    private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeInt(layerIndex);
        out.writeInt(flags);
        out.writeObject(area);

        onSerializing(out);
    }

    protected abstract void onSerializing(ObjectOutputStream out) throws IOException, ClassNotFoundException;

    @Override
    public SerializableFrame clone() {
        return (SerializableFrame) super.clone();
    }
}
