package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class EffectElement {
        private OptionalInt moveX;
        private OptionalInt moveY;

    public EffectElement(int moveX, int moveY) {
        this.moveX = OptionalInt.of(moveX);
        this.moveY = OptionalInt.of(moveY);
    }

    public EffectElement(@NotNull Element element) {
        this.moveX = element.getIntOptional("moveX");
        this.moveY = element.getIntOptional("moveY");
    }

    public OptionalInt getMoveX() {
        return moveX;
    }

    public OptionalInt getMoveY() {
        return moveY;
    }
}
