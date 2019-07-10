package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.commons.utils.ImageUtility;
import com.pokewords.framework.commons.bundles.Bundle;
import com.pokewords.framework.views.Canvas;
import com.pokewords.framework.views.helpers.galleries.Gallery;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * A frame rendered as an image.
 *
 * @author johnny850807 (waterball)
 */
public class ImageFrame extends SerializableFrame {
    /**
     * the stringFrame's rendered point will be seen as its center point.
     */
    public final static int CANVAS_FLAG_RENDER_BY_CENTER = 1;

    private Image image;

    /**
     * The imagePath is on purpose stored for serialization / deserialization.
     * If the image is read from certain gallery with a given picture number, then the imagePath is null
     */
    private String imagePath;

    /**
     * If the image is not read from an imagePath, then it must be read from certain gallery with a given picture number.
     * Otherwise, the gallery will be null and the picture number will be defaulted -1.
     */
    private @Nullable Gallery gallery;
    private int pictureNumberInGallery = -1;


    /**
     * This constructor is protected, use ImageFrameFactory to fromGallery ImageFrame.
     */
    protected ImageFrame(int layerIndex, String imagePath) {
        super(layerIndex);
        // if the imagePath is empty, then it's a Null-Object of imageFrame, i.e. which'll not be rendered.
        if (imagePath != null && !imagePath.isEmpty())
            this.image = ImageUtility.readImageFromResourcesWithCaching(imagePath);
    }

    /**
     * This constructor is protected, use ImageFrameFactory to fromGallery ImageFrame.
     */
    protected ImageFrame(int layerIndex, Gallery gallery, int pictureNumberInGallery) {
        super(layerIndex);
        this.image = gallery.getImage(pictureNumberInGallery);
        this.gallery = gallery;
        this.pictureNumberInGallery = pictureNumberInGallery;
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
        this.image = ImageUtility.readImageFromResourcesWithCaching(imagePath);
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

    @Override
    protected void onDeserializing(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.imagePath = (String) in.readObject();

        if (imagePath == null)
        {
            this.gallery = (Gallery) in.readObject();
            this.pictureNumberInGallery = in.readInt();

            if (gallery == null)
                throw new IllegalStateException("The imageFrame deserializing error occurs: both imagePath and gallery are deserialized null, but one of them is needed for reading image.");
            else
                this.image = gallery.getImage(pictureNumberInGallery);
        }
        else
            this.image = ImageUtility.readImageFromResourcesWithCaching(imagePath);
    }

    @Override
    protected void onSerializing(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject(imagePath);

        if (imagePath == null)
        {
            if (gallery == null)
                throw new IllegalStateException("The imageFrame serializing error occurs: both imagePath and gallery are serialized null, but one of them is needed for reading image.");
            out.writeObject(gallery);
            out.writeInt(pictureNumberInGallery);
        }
    }

}
