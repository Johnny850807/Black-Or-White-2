package com.pokewords.framework.views.helpers.galleries;

import com.pokewords.framework.commons.bundles.Bundle;

/**
 * @author johnny850807 (waterball)
 */
public interface GalleryFactory {

    Gallery create(String galleryType, Bundle properties);

    String getGalleryName(Class<? extends Gallery> type);
}
