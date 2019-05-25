package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

/**
 * @author johnny850807 (waterball)
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BodyElement {
    private OptionalInt x;
    private OptionalInt y;
    private OptionalInt w;
    private OptionalInt h;
    private OptionalInt centerX;
    private OptionalInt centerY;

    public BodyElement(int x, int y, int w, int h, int centerX, int centerY) {
        this.x = OptionalInt.of(x);
        this.y = OptionalInt.of(y);
        this.w = OptionalInt.of(w);
        this.h = OptionalInt.of(h);
        this.centerX = OptionalInt.of(centerX);
        this.centerY = OptionalInt.of(centerY);
    }

    public BodyElement(@NotNull Element element) {
        this.x = element.getKeyValuePairs().getIntOptional("x");
        this.y = element.getKeyValuePairs().getIntOptional("y");
        this.w = element.getKeyValuePairs().getIntOptional("w");
        this.h = element.getKeyValuePairs().getIntOptional("h");
        this.centerX = element.getKeyValuePairs().getIntOptional("centerX");
        this.centerY = element.getKeyValuePairs().getIntOptional("centerY");
    }

    public OptionalInt getX() {
        return x;
    }

    public OptionalInt getY() {
        return y;
    }

    public OptionalInt getW() {
        return w;
    }

    public OptionalInt getH() {
        return h;
    }

    public OptionalInt getCenterX() {
        return centerX;
    }

    public OptionalInt getCenterY() {
        return centerY;
    }
}
