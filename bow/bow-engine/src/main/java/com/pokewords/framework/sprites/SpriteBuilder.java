package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FramesStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

/**
 *
 * How should the API looks like:
 *
 *    SpriteBuilder.createNewSprite()
 *                  .addComponent(myComp)
 *                  .build();
 *
 * @author nyngwang
 */
public class SpriteBuilder {

    private static Sprite sprite;

    /**
     * Allocate memory for a new sprite.
     * @return the current class for fluent API
     */
    public static Class<SpriteBuilder> createNewSprite(
            final String customFSMComponentName, final FramesStateMachineComponent FSMComponent,
            final String customPropertiesComponentName, final PropertiesComponent propertiesComponent
    ) {

        sprite = new Sprite(customFSMComponentName, FSMComponent,
                customPropertiesComponentName, propertiesComponent);

        return SpriteBuilder.class;
    }

    /**
     * Add named component into the current sprite.
     * @param name the string name of the component.
     * @param component the Component to be added to the current sprite.
     * @return the current class for fluent API.
     */
    public static Class<SpriteBuilder> putComponent(String name, Component component) {

        sprite.putCompnent(name, component);
        component.onBoundToSprite(sprite);

        return SpriteBuilder.class;
    }

    /**
     * @return the sprite after built.
     */
    public static Sprite build() {

        return sprite;
    }

}
