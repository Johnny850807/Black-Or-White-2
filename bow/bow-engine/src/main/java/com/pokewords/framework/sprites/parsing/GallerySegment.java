package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.views.Gallery;

public class GallerySegment {
    private String path;
    private int frameWidth;
    private int frameHeight;
    private int row;
    private int column;

    public GallerySegment(String path, int frameWidth, int frameHeight, int row, int column) {
        this.path = path;
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

}
