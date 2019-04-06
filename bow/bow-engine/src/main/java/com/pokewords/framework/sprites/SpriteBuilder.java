package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

/**
 *
 * How should the API looks like:
 *
 *    SpriteBuilder.createNewSprite(FSMname, FSMComp, propName, propComp)
 *                 .addComponent(compName, comp)
 *                 .build();
 *
 * @author nyngwang
 */
public class SpriteBuilder {

    private static Sprite sprite;
    private static boolean enableReplace = true;

    /**
     * Allocate memory for a new sprite.
     * @return the current class for fluent API
     */
    public static Class<SpriteBuilder> createNewSprite(
            final String customFSMComponentName, final FrameStateMachineComponent FSMComponent,
            final String customPropertiesComponentName, final PropertiesComponent propertiesComponent
    ) {

        if (customFSMComponentName.equals(customPropertiesComponentName)) {
            throw new DuplicateComponentNameException("Duplicate component name is not allowed.");
        }

        sprite = new Sprite(customFSMComponentName, FSMComponent,
                customPropertiesComponentName, propertiesComponent);
        enableReplace = true;

        return SpriteBuilder.class;
    }

    /**
     * Add named component to the sprite.
     * @param name the string name of the component.
     * @param component the Component to be added to the current sprite.
     * @return the current class for fluent API.
     */
    public static Class<SpriteBuilder> addComponent(String name, Component component) {

        if (sprite.getComponentByName(name).isPresent()) {
            throw new DuplicateComponentNameException("Duplicate component name is not allowed.");
        }

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
