package com.pokewords.framework.engine.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtility {
    public static Image readImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image readImageFromResources(String imagePath) {
        try {
            return ImageIO.read(Resources.get(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
