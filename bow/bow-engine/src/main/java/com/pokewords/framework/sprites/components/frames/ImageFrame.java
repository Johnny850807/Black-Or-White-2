package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.commons.utils.ImageUtility;
import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.util.Objects;

/**
 * A frame rendered as an image.
 *
 * The width and the height are determined by the sprite at runtime.
 * @author johnny850807 (waterball)
 */
public class ImageFrame extends AbstractFrame {
   protected Image image;
   protected boolean renderWithCenterAdjusted;


    public ImageFrame(int id, int layerIndex, String imagePath) {
        this(id, layerIndex, ImageUtility.readImageFromResources(imagePath), false);
    }

    public ImageFrame(int id, int layerIndex, Image image) {
        this(id, layerIndex, image, false);
    }

    public ImageFrame(int id, int layerIndex, String imagePath, boolean renderWithCenterAdjusted) {
        this(id, layerIndex, ImageUtility.readImageFromResources(imagePath), renderWithCenterAdjusted);
    }

    public ImageFrame(int id, int layerIndex, Image image, boolean renderWithCenterAdjusted) {
        super(id, layerIndex);
        this.image = image;
        this.renderWithCenterAdjusted = renderWithCenterAdjusted;
    }

    @Override
    public void renderItself(Canvas canvas) {
        Objects.requireNonNull(sprite);

        if (renderWithCenterAdjusted)
            canvas.renderImageWithCenterAdjusted(sprite.getX(), sprite.getY(),
                    sprite.getWidth(), sprite.getHeight(), image);
        else
            canvas.renderImage(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), image);
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
        return renderWithCenterAdjusted == that.renderWithCenterAdjusted &&
                image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), image, renderWithCenterAdjusted);
    }
}
