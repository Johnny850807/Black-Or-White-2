package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.engine.utils.ImageUtility;
import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.util.Objects;

public class ImageFrame extends AbstractFrame implements Frame {
   protected int width;
   protected int height;
   protected Image image;
   protected boolean renderWithCenterAdjusted;

    public ImageFrame(int id, int layerIndex, int width, int height, String imagePath) {
        this(id, layerIndex, width, height, ImageUtility.readImageFromResources(imagePath), false);
    }

    public ImageFrame(int id, int layerIndex, int width, int height, Image image) {
        this(id, layerIndex, width, height, image, false);
    }

    public ImageFrame(int id, int layerIndex, String imagePath) {
        this(id, layerIndex, ImageUtility.readImageFromResources(imagePath), false);
    }

    public ImageFrame(int id, int layerIndex, Image image) {
        this(id, layerIndex, image.getWidth(null), image.getHeight(null), image, false);
    }

    public ImageFrame(int id, int layerIndex, int width, int height, String imagePath, boolean renderWithCenterAdjusted) {
        this(id, layerIndex, width, height, ImageUtility.readImageFromResources(imagePath), renderWithCenterAdjusted);
    }

    public ImageFrame(int id, int layerIndex, String imagePath, boolean renderWithCenterAdjusted) {
        this(id, layerIndex, ImageUtility.readImageFromResources(imagePath), renderWithCenterAdjusted);
    }

    public ImageFrame(int id, int layerIndex, Image image, boolean renderWithCenterAdjusted) {
        this(id, layerIndex, image.getWidth(null), image.getHeight(null), image, renderWithCenterAdjusted);
    }

    public ImageFrame(int id, int layerIndex, int width, int height, Image image, boolean renderWithCenterAdjusted) {
        super(id, layerIndex);
        this.width = width;
        this.height = height;
        this.image = image;
        this.renderWithCenterAdjusted = renderWithCenterAdjusted;
    }

    @Override
    public void renderItself(Canvas canvas) {
        if (renderWithCenterAdjusted)
            canvas.renderImageWithCenterAdjusted(sprite.getX(), sprite.getY(), width, height, image);
        else
            canvas.renderImage(sprite.getX(), sprite.getY(), width, height, image);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public boolean isRenderWithCenterAdjusted() {
        return renderWithCenterAdjusted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ImageFrame that = (ImageFrame) o;
        return width == that.width &&
                height == that.height &&
                renderWithCenterAdjusted == that.renderWithCenterAdjusted &&
                image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height, image, renderWithCenterAdjusted);
    }
}
