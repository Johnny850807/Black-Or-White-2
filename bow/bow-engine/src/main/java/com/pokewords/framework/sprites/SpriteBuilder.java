package com.pokewords.framework.sprites;

public class SpriteBuilder {


    /**
     * Requirements:
     *  1. general
     *  2.
     *
     * How should the API looks like:
     *
     *  SpriteBuilder.createNewSprite().setFrames().setB().setC().build();
     *
     *
     * @return
     */


    private static Sprite sprite;

    public static Class<SpriteBuilder> createNewSprite() {

        sprite = new Sprite();

        return SpriteBuilder.class;
    }

    public static Class<SpriteBuilder> methodB() {
        return SpriteBuilder.class;
    }

    public static Class<SpriteBuilder> methodC() {
        return SpriteBuilder.class;
    }

}
