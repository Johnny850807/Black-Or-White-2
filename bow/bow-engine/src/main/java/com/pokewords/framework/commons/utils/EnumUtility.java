package com.pokewords.framework.commons.utils;

import java.util.Arrays;

/**
 * @author johnny850807 (waterball)
 */
public class EnumUtility {

    /**
     * @param enumType the class of the enum
     * @param name the enum's name
     * @return the actual enum evaluated from the name
     * @throws IllegalArgumentException the name is not found under the enum class
     */
    public static <T> T evalEnum(Class<T> enumType, String name) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.toString().equals(name))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(
                        String.format("The enum %s is not found under the enum class %s.",
                                name, enumType)));
    }

    public static boolean enumExists(Class<?> enumType, String name) {
        return Arrays.stream(enumType.getEnumConstants())
                .anyMatch(e -> e.toString().equals(name));
    }
}
