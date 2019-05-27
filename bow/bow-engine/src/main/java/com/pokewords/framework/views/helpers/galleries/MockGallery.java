package com.pokewords.framework.views.helpers.galleries;

import com.pokewords.framework.commons.Range;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

/**
 * A mock gallery.
 * @author johnny850807 (waterball)
 */
public class MockGallery implements Gallery {

    @Override
    public Image getImage(int pic) {
        return new Image(){
            @Override
            public int getWidth(ImageObserver observer) {
                return 0;
            }
            @Override
            public int getHeight(ImageObserver observer) {
                return 0;
            }

            @Override
            public ImageProducer getSource() {
                return null;
            }

            @Override
            public Graphics getGraphics() {
                return null;
            }

            @Override
            public Object getProperty(String name, ImageObserver observer) {
                return null;
            }
        };
    }

    @Override
    public Range getPictureRange() {
        return new Range(0, 0);
    }
}
