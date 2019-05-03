package com.pokewords.framework.engine.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility {
    public static String read(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes);
    }
}
