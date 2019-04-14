package com.pokewords.framework.sprites.parsing;

public interface Script {

    /**
     * @param startPic the index of the start picture
     * @param endPic the index of the end picture
     * @param gallerySegment the gallerySegment added to the range given by the start and the end index
     */
    void addGallerySegment(int startPic, int endPic, GallerySegment gallerySegment);

    /**
     * @param pictureNumber the pic value
     * @return the gallery image's path which contains the desired picture
     */
    GallerySegment getGallerySegment(int pictureNumber);

    /**
     * Set the script
     * @param text
     */
    void setText(String text);

    /**
     * @return the script's text-based String
     */
    String getText();
}
