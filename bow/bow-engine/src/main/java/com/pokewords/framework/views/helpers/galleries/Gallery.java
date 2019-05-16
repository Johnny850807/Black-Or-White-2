package com.pokewords.framework.views.helpers.galleries;

import com.pokewords.framework.commons.Range;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public interface Gallery {
    /**
     * @param pic the picture's number
     * @return is the pic within the gallery's picture range
     */
    default boolean containsPicture(int pic) {
        return getPictureRange().within(pic);
    }

    /**
     * @param pic the picture's number
     * @return the #pic image
     */
    Image getImage(int pic);


    /**
     * @return the Range of the picture number
     */
    Range getPictureRange();
}
