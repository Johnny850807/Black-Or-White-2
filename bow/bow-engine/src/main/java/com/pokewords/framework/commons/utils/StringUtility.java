package com.pokewords.framework.commons.utils;

import java.util.Map;

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

    /**
     * @return an array of characters as strings from 'a'~'z' then 'A'~'Z'
     */
    public static String[] characters(){
        String[] characters = new String[52];

        String[] lowerCaseCharacters = lowerCaseCharacters();
        String[] upperCaseCharacters = upperCaseCharacters();

        for (int i = 0; i < 26; i++) {
            characters[i] = lowerCaseCharacters[i];
        }

        for (int i = 26; i < 52; i++) {
            characters[i] = upperCaseCharacters[i-26];
        }
        return characters;
    }

    /**
     * @return an array of lowercase characters as strings
     */
    public static String[] lowerCaseCharacters(){
        String[] lowerCaseCharacters = new String[26];
        for (int i = 97; i < 123; i++) {
            lowerCaseCharacters[i-97] = String.valueOf((char)i);
        }
        return lowerCaseCharacters;
    }

    /**
     * @return an array of uppercase characters as strings
     */
    public static String[] upperCaseCharacters(){
        String[] lowerCaseCharacters = new String[26];
        for (int i = 65; i < 91; i++) {
            lowerCaseCharacters[i-65] = String.valueOf((char)i);
        }
        return lowerCaseCharacters;
    }


    public static String toString(Map map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (Object key : map.keySet()) {
            mapAsString.append(key).append("=").append(map.get(key)).append(", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }
}
