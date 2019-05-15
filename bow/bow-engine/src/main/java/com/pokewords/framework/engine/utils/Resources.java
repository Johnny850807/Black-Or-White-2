package com.pokewords.framework.engine.utils;

import java.io.File;

public class Resources {

    @SuppressWarnings("ConstantConditions")
    public static File get(String path) {
        return new File(Thread.currentThread().getContextClassLoader().getResource(path).getFile());
    }

}
