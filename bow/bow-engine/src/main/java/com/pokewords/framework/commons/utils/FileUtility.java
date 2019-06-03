package com.pokewords.framework.commons.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility {
    public static String read(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String read(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
}
