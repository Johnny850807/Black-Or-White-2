package com.pokewords.framework.commons.tools;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public class MakeSequenceImageNames {
    public static void make(File directory) {
        String dirPath = directory.getPath();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String extension = file.getName().split("\\.")[1];
            String[] numberSplits = file.getName().split("\\D+");  //get numbers within string
            int number = Integer.parseInt(numberSplits[numberSplits.length-1]);
            if (!file.renameTo(Paths.get(dirPath, number + "." + extension).toFile()))
                throw new IllegalStateException("Rename failed.");
        }
    }

    public static void main(String[] args) {
        MakeSequenceImageNames.make(
                new File("D:\\NativeGit\\Black-Or-White-Java\\BlackOrWhite\\pics\\AI\\Black\\Halt"));
    }
}
