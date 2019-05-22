package com.pokewords.framework.commons.utils;

import java.io.File;

public class Resources {

    /**
     * @param path the resource path
     * @return the file under resources
     */
    @SuppressWarnings("ConstantConditions")
    public static File get(String path) {
        return new File(Thread.currentThread().getContextClassLoader().getResource(path).getFile());
    }

}
