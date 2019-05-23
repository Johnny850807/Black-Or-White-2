package com.pokewords.framework.commons.utils;

import java.io.File;
import java.net.URL;

public class Resources {

    /**
     * @param path the resource path
     * @return the file under resources
     */
    public static File get(String path) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null)
            throw new IllegalArgumentException("No file (" + path + ") found.");
        return new File(url.getFile());
    }

}
