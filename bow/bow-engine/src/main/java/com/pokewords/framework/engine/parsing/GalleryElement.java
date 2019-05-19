package com.pokewords.framework.engine.parsing;


import com.pokewords.framework.commons.Range;
import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.views.helpers.galleries.Gallery;
import com.pokewords.framework.views.helpers.galleries.MockGallery;
import com.pokewords.framework.views.helpers.galleries.SequenceGallery;
import com.pokewords.framework.views.helpers.galleries.SheetGallery;

/**
 * @author johnny850807 (waterball)
 */
public class GalleryElement {
    public enum GalleryType {
        sheet, sequence, mock
    }

    private Element galleryElement;
    private String galleryType;
    private Range range;

    public GalleryElement(Element galleryElement) {
        this.galleryElement = galleryElement;
        this.galleryType = galleryElement.getName();
        this.range = new Range(galleryElement.getIntByKey("startPic"), galleryElement.getIntByKey("endPic"));
    }

    public String getGalleryType() {
        return galleryType;
    }

    public Range getRange() {
        return range;
    }

    public Gallery toGallery() {
        return new SimpleFactory().createGallery(galleryType);
    }

    public class SimpleFactory {
        public Gallery createGallery(String galleryType) {
            GalleryType typeName = GalleryType.valueOf(galleryType.trim().toLowerCase());

            switch (typeName) {
                case sheet:
                    return new SheetGallery(galleryElement.getStringByKey("path"), range,
                            galleryElement.getIntByKey("row"), galleryElement.getIntByKey("col"),
                            galleryElement.getIntByKeyOptional("padding").orElse(0));
                case sequence:
                    return new SequenceGallery(galleryElement.getStringByKey("path"), range);
                case mock:
                    return new MockGallery();
                default:
                    throw new IllegalArgumentException(
                            String.format("The gallery of type %s does not exist.", typeName));
            }
        }
    }
}
