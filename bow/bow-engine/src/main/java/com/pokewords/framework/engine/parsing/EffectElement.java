package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;

public class EffectElement {
    private int moveX;
    private int moveY;

    public EffectElement(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public EffectElement(Element element) {
        this.moveX = element.getIntByKeyOptional("moveX").orElse(0);
        this.moveY = element.getIntByKeyOptional("moveY").orElse(0);
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }
}
