package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FramesStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

public class SpriteBuilder {


    /**
     * Requirements:
     *  1. general
     *  2.
     *
     * How should the API looks like:
     *
     *  SpriteBuilder.createNewSprite()
     *                .setView(myView)
     *                .setProperties(myProp)
     *                .addComponent(myComp)
     *                .build();
     *
     */

    private static Sprite sprite;

    public static Class<SpriteBuilder> createNewSprite() {

        sprite = new Sprite();

        return SpriteBuilder.class;
    }

    public static Class<SpriteBuilder> setView(FramesStateMachineComponent view) {

        sprite.setViewComponent(view);
        view.onBoundToSprite(sprite);

        return SpriteBuilder.class;
    }

    public static Class<SpriteBuilder> setProperties(PropertiesComponent properties) {

        sprite.setPropertiesComponent(properties);
        properties.onBoundToSprite(sprite);

        return SpriteBuilder.class;
    }

    public static Class<SpriteBuilder> addComponent(String name, Component component) {

        sprite.addCompnent(name, component);
        component.onBoundToSprite(sprite);

        return SpriteBuilder.class;
    }
    public static Sprite build() {

        return sprite;
    }

}
