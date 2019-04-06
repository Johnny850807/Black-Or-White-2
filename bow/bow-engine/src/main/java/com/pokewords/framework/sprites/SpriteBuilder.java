package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.Script;
import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

/**
 *
 * For testability one parameter per method.
 *
 * What will the API look like:
 *
 *    SpriteBuilder.setScript(script)
 *                 .setFSMComponent(fsmComp)
 *                 .addComponent(compName, comp)
 *                 .build();
 *
 *
 *
 * New mission:
 *
 *    Finite State Machine:
 *      animation pictures
 *      transitions
 *      definition is from the client script
 *    Some interfaces for user to construct the FSMComponent with ease.
 *
 * component Inject method last line within build()
 *
 * @author nyngwang
 */
public class SpriteBuilder {

    // frameFactory here
//    private static Sprite sprite;
//    private static boolean script
//    private static boolean FSMComponentIsSet;
//    private static boolean propertiesComponentIsSet;
//
//

    public static Class<SpriteBuilder> setScript(Script script) {



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
