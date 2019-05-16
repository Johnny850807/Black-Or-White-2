package com.pokewords.framework.engine.parsing;


import com.pokewords.framework.commons.Range;
import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.views.helpers.galleries.Gallery;
import com.pokewords.framework.views.helpers.galleries.SequenceGallery;
import com.pokewords.framework.views.helpers.galleries.SheetGallery;

/**
 * @author johnny850807 (waterball)
 */
public class GalleryElement {
    public enum GalleryType {
        sheet, sequence
    }

    private Element galleryElement;
    private String galleryType;
    private Range range;
    private int row;
    private int column;

    public GalleryElement(Element galleryElement) {
        this.galleryElement = galleryElement;
        this.galleryType = galleryElement.getElementName();
        this.range = new Range(galleryElement.getIntByKey("startPic"), galleryElement.getIntByKey("endPic"));
        this.row = galleryElement.getIntByKey("row");
        this.column = galleryElement.getIntByKey("col");
    }

    public String getGalleryType() {
        return galleryType;
    }

    public Range getRange() {
        return range;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Gallery toGallery() {
        return new SimpleFactory().createGallery(galleryType);
    }

    public class SimpleFactory {
        public Gallery createGallery(String galleryType) {
            GalleryType typeName = GalleryType.valueOf(galleryType.trim().toLowerCase());

            switch (typeName) {
                case sheet:
                    return new SheetGallery(galleryElement.getStringByKey("path"), range, row, column,
                            galleryElement.getIntByKeyOptional("padding").orElse(0));
                case sequence:
                    return new SequenceGallery(galleryElement.getStringByKey("path"), range);
                default:
                    throw new IllegalArgumentException(
                            String.format("The gallery of type %s does not exist.", typeName));
            }
        }
    }
}
