package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.helpers.galleries.Gallery;

public class ImageFrameFactory {

    public static ImageFrame fromGallery(int layerIndex, Gallery gallery, int pictureNumberInGallery)  {
        return new ImageFrame(layerIndex, gallery, pictureNumberInGallery);
    }

    public static ImageFrame fromPath(int layerIndex, String imagePath) {
        return new ImageFrame(layerIndex, imagePath);
    }

    public static ImageFrame emptyImageFrame() {
        return new EmptyImageFrame();
    }
}
