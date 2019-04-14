package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.views.Gallery;

/**
 * @author johnny850807
 */
public class GallerySegment {
    private String path;
    private int startPic;
    private int endPic;
    private int frameWidth;
    private int frameHeight;
    private int row;
    private int column;

    public GallerySegment(String path, int startPic, int endPic, int frameWidth, int frameHeight, int row, int column) {
        this.path = path;
        this.startPic = startPic;
        this.endPic = endPic;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.row = row;
        this.column = column;
    }

    public Gallery toGallery(){
        return new Gallery(frameWidth, frameHeight, row, column);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getStartPic() {
        return startPic;
    }

    public void setStartPic(int startPic) {
        this.startPic = startPic;
    }

    public int getEndPic() {
        return endPic;
    }

    public void setEndPic(int endPic) {
        this.endPic = endPic;
    }
}
