package com.pokewords.framework.views.helpers;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public interface Gallery {
    /**
     * @param pic the picture's number
     * @return is the pic within the gallery's picture range
     */
    boolean containsPicture(int pic);

    /**
     * @param pic the picture's number
     * @return the #pic image
     */
    Image getImage(int pic);

    /**
     * @param row the row number
     * @param col the column number
     * @return the image at the grid (row, col)
     */
    Image getImage(int row, int col);
}
