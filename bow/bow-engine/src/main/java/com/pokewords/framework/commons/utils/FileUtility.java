package com.pokewords.framework.commons.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility {
    public static String read(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
