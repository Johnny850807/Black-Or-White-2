package com.pokewords.framework.views.helpers.galleries;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.commons.bundles.Bundle;

import java.util.HashMap;

/**
 * @author johnny850807 (waterball)
 */
public class DefaultGalleryFactory implements GalleryFactory {
    private final static HashMap<Class<? extends Gallery>, String> galleryNameMap = new HashMap<>();
    private final static String SHEET = "sheet";
    private final static String SEQUENCE = "sequence";

    static {
        setupGalleryNameMap();
    }

    private static void setupGalleryNameMap() {
        galleryNameMap.put(SheetGallery.class, SHEET);
        galleryNameMap.put(SequenceGallery.class, SEQUENCE);
    }

    @Override
    public Gallery create(String galleryType, Bundle properties) {
        galleryType = galleryType.toLowerCase();
        switch (galleryType)
        {
            case SHEET:
                return createSheetGallery(properties);
            case SEQUENCE:
                return createSequenceGallery(properties);
                default:
                    throw new IllegalArgumentException("Cannot recognize the gallery of type " + galleryType + ".");
        }
    }

    private SheetGallery createSheetGallery(Bundle properties) {
        return new SheetGallery(properties.getString("path"),
                readGalleryRange(properties),
                properties.getInt("row"), properties.getInt("col"),
                properties.getIntOptional("padding").orElse(0));
    }

    private SequenceGallery createSequenceGallery(Bundle properties) {
        return new SequenceGallery(properties.getString("path"), readGalleryRange(properties));
    }

    public Range readGalleryRange(Bundle properties) {
        return new Range(properties.getInt("startPic"), properties.getInt("endPic"));
    }

    @Override
    public String getGalleryName(Class<? extends Gallery> type) {
        return galleryNameMap.get(type);
    }
}
