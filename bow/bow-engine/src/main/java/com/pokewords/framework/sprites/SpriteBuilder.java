package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.engine.exceptions.FrameStateMachineComponentIsRequiredException;
import com.pokewords.framework.engine.exceptions.PropertiesComponentIsRequiredException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.FrameStateMachineScriptParser;

/**
 *
 * API Usage:
 *
 *    SpriteBuilder builder = new SpriteBuilder(iocFactory);
 *    builder.init()
 *           .addScript(script)
 *           .addListener(listener)
 *           .addPropertiesComponent(propComp)
 *           .addComponent(compName, comp)
 *           .build();
 *
 * What to do next:
 *
 *    - Parse the Script (NEED DISCUSSION with @Lin)
 *    - IoC Factory::Create Frame -> FSMComponent
 *    - Is Interpret appropriate?
 *
 * @author nyngwang
 */
public class SpriteBuilder {

    // Sprite Related
    private Sprite sprite;
    private FrameStateMachineComponent fsmComponent;
    private PropertiesComponent propertiesComponent;

    // FSM Related
    private Script script;
    FrameStateMachineScriptParser parser;
        // this guy has to create Frame node
        // ParsingException if script GG
            // Need comprehensive error message: line # in script

    
    /**
     * The constructor of SpriteBuilder.
     * @param iocFactory To do dependency injection.
     */
    public SpriteBuilder(IocFactory iocFactory) {

        sprite = null;
        fsmComponent = null;
        propertiesComponent = null;

        script = null;
        parser = iocFactory.frameStateMachineScriptParser();
    }

    /**
     * Initialize before creating a new sprite.
     * @return The current builder.
     */
    public SpriteBuilder init() {
        sprite = null;
        fsmComponent = null;
        propertiesComponent = null;
        script = null;

        return this;
    }

    /**
     * Add a script for the current sprite.
     * @param script The script file required to define FSM.
     * @return The current builder.
     */
    public SpriteBuilder addScript(Script script) {
        this.script = script;
        return this;
    }

    /**
     * Add Properties Component to the current sprite.
     * @param propertiesComponent The Properties Component required to define FSM.
     * @return The current builder.
     */
    public SpriteBuilder addPropertiesComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
        prepareSprite();
        return this;
    }

    public SpriteBuilder addComponent(String name, Component component) {

        checkSpriteIsReady();

        if (sprite.getComponentByName(name).isPresent()) {
            throw new DuplicateComponentNameException("Duplicate component name is not allowed.");
        }

        sprite.putCompnent(name, component);
        component.onBoundToSprite(sprite);

        return this;
    }

    /**
     * Return the built sprite.
     * @return The sprite.
     */
    public Sprite build() {
        checkSpriteIsReady();
        ComponentInjector.inject(sprite);
        return sprite;
    }

    // Utility Functions
    /**
     * Try to create a sprite when mandatory components are ready.
     */
    private void prepareSprite() {
        if (fsmComponent == null || propertiesComponent == null) {
            return;
        }
        sprite = new Sprite(fsmComponent, propertiesComponent);
    }

    /**
     * Check the sprite is ready before access.
     */
    private void checkSpriteIsReady() {
        if (fsmComponent == null) {
            throw new FrameStateMachineComponentIsRequiredException(
                    "FrameStateMachineComponent is required.");
        }
        if (propertiesComponent == null) {
            throw new PropertiesComponentIsRequiredException(
                    "PropertiesComponent is required.");
        }
    }

}
