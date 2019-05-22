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
    /**
     * the stringFrame's rendered point will be seen as its center point.
     */
    public final static int CANVAS_FLAG_RENDER_BY_CENTER = 1;

   protected Image image;

    public ImageFrame(int id, int layerIndex, String imagePath) {
        this(id, layerIndex, ImageUtility.readImageFromResources(imagePath));
    }

    public ImageFrame(int id, int layerIndex, Image image) {
        super(id, layerIndex);
        this.image = image;
    }

    public ImageFrame flags(int flags) {
        this.flags = flags;
        return this;
    }

    @Override
    public void renderItself(Canvas canvas) {
        Objects.requireNonNull(sprite);

        canvas.render(this);
    }

    public void setImage(String imagePath) {
        this.image = ImageUtility.readImageFromResources(imagePath);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ImageFrame that = (ImageFrame) o;
        return image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), image);
    }
}
