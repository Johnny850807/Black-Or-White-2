package com.pokewords.framework.engine.utils;

public class StringUtility {
    /**
     * @param s the string
     * @return whether the string is null or empty (length == 0)
     */
    public static boolean isNullOrEmpty(String s){
        return s == null || s.isEmpty();
    }

    /**
     * @param s a list of strings
     * @return whether any of the strings is null or empty
     */
    public static boolean anyNullOrEmpty(String ...s){
        for (String s1 : s) {
            if(isNullOrEmpty(s1))
                return true;
        }
        return false;
    }
}
