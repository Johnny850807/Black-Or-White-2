package com.pokewords.framework.sprites.parsing;

/**
 * This is called LinScript so that we always remember the time Lin contributed to the Script Definition.
 */
public class LinScript implements Script {

    private String text;

    public LinScript(String text) {
        this.text = text;
    }

    @Override
    public void addGallerySegment(int startPic, int endPic, GallerySegment gallerySegment) {

    }

    @Override
    public GallerySegment getGallerySegment(int pictureNumber) {
        return null;
    }


    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return null;
    }
}
