package com.pokewords.framework.commons.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Remain only the distinct images within a directory.
 * @author johnny850807 (waterball)
 */
public class RemainOnlyDistinctImages {

    public static void applyToDirectory(File directory) throws IOException {
        Set<byte[]> pixelsSet = new HashSet<>();
        Set<BufferedImage> distinctImages = new HashSet<>();
        File[] files = Objects.requireNonNull(directory.listFiles());
        OuterLoop:
        for (File imageFile : files) {
            BufferedImage image = ImageIO.read(imageFile);
            byte[] imagePixels = getPixels(image);
            for (byte[] pixels : pixelsSet) {
                if (Arrays.equals(imagePixels, pixels))
                    continue OuterLoop;
            }
            pixelsSet.add(imagePixels);
            distinctImages.add(image);
        }

        for (File file : files) {
            if (!file.delete())
                throw new IOException("The file " + file.getPath() + " cannot be deleted.");
        }
        outputImages(directory, distinctImages);
    }

    private static byte[] getPixels(BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    private static void outputImages(File directory, Set<BufferedImage> distinctImages) throws IOException {
        int pic = 0;
        for (BufferedImage distinctImage : distinctImages) {
            File imageFile = Paths.get(directory.getPath(), String.format("%d.png", pic++)).toFile();
            ImageIO.write(distinctImage, "png", imageFile);
        }
    }

    public static void main(String[] args) throws IOException {
        RemainOnlyDistinctImages.applyToDirectory(
                new File("D:\\NativeGit\\Black-Or-White-Java\\BlackOrWhite\\pics\\AI\\Black\\Halt"));
    }
}
