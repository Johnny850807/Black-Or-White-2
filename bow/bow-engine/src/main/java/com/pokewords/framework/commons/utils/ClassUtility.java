package com.pokewords.framework.commons.utils;

public class ClassUtility {

    public static Class<?> forName(String classPath) {
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
