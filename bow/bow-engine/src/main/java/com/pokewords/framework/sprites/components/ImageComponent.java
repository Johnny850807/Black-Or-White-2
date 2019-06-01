package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.ImageFrame;

import java.awt.*;

public class ImageComponent extends FrameComponent<ImageFrame> {
    public ImageComponent(ImageFrame frame) {
        super(frame);
    }

    public void setImage(String imagePath) {
        getFrame().setImage(imagePath);
    }

    public void setImage(Image image) {
        getFrame().setImage(image);
    }
}
