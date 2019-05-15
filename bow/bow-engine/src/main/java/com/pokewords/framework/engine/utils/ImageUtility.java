package com.pokewords.framework.engine.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageUtility {
    public static Image readImage(String imagePath) {
        try {
            return ImageIO.read(Resources.get(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
