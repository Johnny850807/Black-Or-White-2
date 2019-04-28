package com.pokewords.framework.sprites.parsing;

public interface Script {

    void addSegment(Segment segment);

    // Gallery Segment
    void addGallerySegment(GallerySegment gallerySegment);
    GallerySegment getGallerySegment(int pictureNumber);


    // Frame Segment
    void addFrameSegment(FrameSegment frameSegment);

    FrameSegment getFrameSegment();

    /**
     * Set the script's text-based String.
     * @param text text-based String.
     */
    void setText(String text);

    /**
     * @return the script's text-based String
     */
    String getText();
}
