package com.pokewords.framework.engine.utils;

import java.util.Arrays;

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

    public static String[] lowerCaseCharacters(){
        String[] lowerCaseCharacters = new String[26];
        for (int i = 97; i < 123; i++) {
            lowerCaseCharacters[i-97] = String.valueOf((char)i);
        }
        return lowerCaseCharacters;
    }

    public static String[] upperCaseCharacters(){
        String[] lowerCaseCharacters = new String[26];
        for (int i = 65; i < 91; i++) {
            lowerCaseCharacters[i-65] = String.valueOf((char)i);
        }
        return lowerCaseCharacters;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(StringUtility.lowerCaseCharacters()));
        System.out.println(Arrays.toString(StringUtility.upperCaseCharacters()));
        System.out.println(Arrays.toString(StringUtility.characters()));
    }
}
