package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.utils.ImageUtility;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;


/**
 * A immutable renderable component that can be rendered as a image.
 * @author johnny850807 (waterball)
 */
public class ImageComponent extends CloneableComponent implements Renderable {
    private Image image;
    private int layerIndex;
    private int width;
    private int height;
    private ImageFrame imageFrame;
    private Collection<ImageFrame> imageFrameSingletonCollection;

    private Sprite sprite;

    public ImageComponent(String imagePath, int layerIndex) {
        this(ImageUtility.readImage(imagePath), layerIndex);
    }

    public ImageComponent(Image image, int layerIndex) {
        this(image, layerIndex, image.getWidth(null), image.getHeight(null));
    }

    public ImageComponent(String imagePath, int layerIndex, int width, int height) {
        this(ImageUtility.readImage(imagePath), layerIndex, width, height);
    }

    public ImageComponent(Image image, int layerIndex, int width, int height) {
        this.layerIndex = layerIndex;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    public void onComponentInjected() {
        imageFrame = new ImageFrame(sprite, 0, layerIndex, width, height, image);
        imageFrameSingletonCollection = Collections.singleton(imageFrame);
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return imageFrameSingletonCollection;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return imageFrameSingletonCollection;
    }

    public void setWidth(int width) {
        this.width = width;

        if (imageFrame != null)
            imageFrame.setWidth(width);
    }

    public void setHeight(int height) {
        this.height = height;

        if (imageFrame != null)
            imageFrame.setHeight(height);
    }
}
