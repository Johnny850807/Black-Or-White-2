package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.engine.utils.ImageUtility;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;

import java.awt.*;

public class ImageFrame implements Frame {
    private Sprite sprite;
    private int id;
    private int layerIndex;
    private Image image;

    public ImageFrame(Sprite sprite, int id, int layerIndex, String imagePath) {
        this.sprite = sprite;
        this.id = id;
        this.layerIndex = layerIndex;
        this.image = ImageUtility.readImage(imagePath);
    }

    public ImageFrame(Sprite sprite, int id, int layerIndex, Image image) {
        this.sprite = sprite;
        this.id = id;
        this.layerIndex = layerIndex;
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
        canvas.renderImage(sprite.getX(), sprite.getY(), image);
    }
}
