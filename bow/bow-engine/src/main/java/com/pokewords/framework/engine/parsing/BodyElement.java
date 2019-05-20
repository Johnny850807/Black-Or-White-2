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
        this.x = element.getIntByKeyOptional("x");
        this.y = element.getIntByKeyOptional("y");
        this.w = element.getIntByKeyOptional("w");
        this.h = element.getIntByKeyOptional("h");
        this.centerX = element.getIntByKeyOptional("centerX");
        this.centerY = element.getIntByKeyOptional("centerY");
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
