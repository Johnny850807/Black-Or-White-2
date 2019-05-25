package com.pokewords.framework.commons.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ImageUtility {
    public final static Map<String, Image> imageCachedMap = new HashMap<>(); //<image's path, image>

    public static Image readImage(File file) {
        try {
            if (imageCachedMap.containsKey(file.getPath()))
                return imageCachedMap.get(file.getPath());
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read an image under resources and cache that result,
     * next time when the same path is requested, this will return the previously cached image.
     */
    public static Image readImageFromResourcesWithCaching(String imagePath) {
        try {
            if (imageCachedMap.containsKey(imagePath))
                return imageCachedMap.get(imagePath);
            else
            {
                Image image = ImageIO.read(Resources.get(imagePath));
                imageCachedMap.put(imagePath, image);
                return image;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
