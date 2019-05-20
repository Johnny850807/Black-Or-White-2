package com.pokewords.framework.commons.utils;

import java.util.Arrays;

public class EnumUtility {
    public static <T> T evalEnum(Class<T> enumType, String name) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.toString().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(
                        String.format("The enum %s is not found under the enum class %s.",
                                name, enumType)));
    }
}
