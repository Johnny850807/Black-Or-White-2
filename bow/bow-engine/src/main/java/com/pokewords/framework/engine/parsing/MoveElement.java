package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MoveElement {
        private OptionalInt moveX;
        private OptionalInt moveY;

    public MoveElement(int moveX, int moveY) {
        this.moveX = OptionalInt.of(moveX);
        this.moveY = OptionalInt.of(moveY);
    }

    public MoveElement(@NotNull Element element) {
        this.moveX = element.getKeyValuePairs().getIntOptional("moveX");
        this.moveY = element.getKeyValuePairs().getIntOptional("moveY");
    }

    public OptionalInt getMoveX() {
        return moveX;
    }

    public OptionalInt getMoveY() {
        return moveY;
    }
}
