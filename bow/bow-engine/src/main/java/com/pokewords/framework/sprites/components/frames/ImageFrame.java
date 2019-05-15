package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.engine.utils.ImageUtility;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class ImageFrame implements Frame {
    private Sprite sprite;
    private int id;
    private int layerIndex;
    private int width;
    private int height;
    private Image image;

    public ImageFrame(Sprite sprite, int id, int layerIndex, int width, int height, String imagePath) {
        this(sprite, id, layerIndex, width, height, ImageUtility.readImage(imagePath));
    }

    public ImageFrame(Sprite sprite, int id, int layerIndex, String imagePath) {
        this(sprite, id, layerIndex, ImageUtility.readImage(imagePath));
    }

    public ImageFrame(Sprite sprite, int id, int layerIndex, Image image) {
        this(sprite, id, layerIndex, image.getWidth(null), image.getHeight(null), image);
    }

    public ImageFrame(Sprite sprite, int id, int layerIndex, int width, int height, Image image) {
        this.sprite = sprite;
        this.id = id;
        this.layerIndex = layerIndex;
        this.width = width;
        this.height = height;
        this.image = image;
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
    public void renderItself(Canvas canvas) {
        canvas.renderImage(sprite.getX(), sprite.getY(), width, height, image);
    }

    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
