package com.pokewords.framework.commons.tools;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MakeSequenceImageNames {
    public static void make(File directory, int startNumber) {
        String dirPath = directory.getPath();

        List<File> sortedPicFiles = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                                .sorted(Comparator.comparingInt(MakeSequenceImageNames::getFileNumberPart))
                                .collect(Collectors.toList());

        for (File file : sortedPicFiles) {
            String extension = file.getName().split("\\.")[1];
            int picNumber = startNumber ++;
            if (!file.renameTo(Paths.get(dirPath, picNumber + "." + extension).toFile()))
                throw new IllegalStateException("Rename failed.");
        }
    }

    public static void make(File directory) {
        make(directory, 0);
    }

    private static int getFileNumberPart(File file) {
        String[] numberSplits = file.getName().split("\\D+");  //get numbers within string
        return Integer.parseInt(numberSplits[numberSplits.length-1]);
    }

    public static void main(String[] args) {
        MakeSequenceImageNames.make(
                new File("D:\\NativeGit\\Black-Or-White-2\\bow\\bow-core\\src\\main\\resources\\assets\\pics\\ai\\sniperTank")
        , 0);
    }
}
