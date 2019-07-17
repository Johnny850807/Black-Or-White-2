package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The null-object of ImageFrame which doesn't render anything.
 * @author johnny850807 (waterball)
 */
public class EmptyImageFrame extends ImageFrame {
    public static final EmptyImageFrame SINGLETON = new EmptyImageFrame();

    public EmptyImageFrame() {
        super(0, "");
    }

    @Override
    public void renderItself(Canvas canvas) { }

    @Override
    protected void onSerializing(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        super.onSerializing(out);
    }

    @Override
    protected void onDeserializing(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.onDeserializing(in);
    }
}
