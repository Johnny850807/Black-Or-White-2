package com.pokewords.framework.sprites.parsing;

public interface Script {
    /**
     * @param pictureNumber the pic value
     * @return the gallery image's path which contains the desired picture
     */
    GallerySegment getGallerySegment(int pictureNumber);
}
