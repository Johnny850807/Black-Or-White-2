package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

/**
 * @author johnny850807 (waterball)
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AreaElement {
    private OptionalInt w;
    private OptionalInt h;

    public AreaElement(@NotNull Element element) {
        this.w = element.getIntOptional("w");
        this.h = element.getIntOptional("h");
    }

    public OptionalInt getW() {
        return w;
    }

    public OptionalInt getH() {
        return h;
    }

}
