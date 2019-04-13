package com.pokewords.framework.sprites;

import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.parsing.FrameSegment;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.engine.exceptions.FrameStateMachineComponentIsRequiredException;
import com.pokewords.framework.engine.exceptions.PropertiesComponentIsRequiredException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.FrameStateMachineScriptParser;
import com.pokewords.framework.sprites.parsing.Script1;

import java.util.function.BiConsumer;

/**
 *
 * API Usage:
 *
 *    SpriteBuilder builder = new SpriteBuilder(iocFactory);
 *    builder.init()
 *             .init(iocFactory)            // Now changing parser is possible after builder initialized.
 *           .setupParser(script, listener)
 *             .setupParser(script)         // provide no listener, use default.
 *           .setFSMComponent(fsmComp)      // Support adding fsmComp directly.
 *           .setPropertiesComponent(propComp)
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

    public static void main(String[] args) {
        SpriteBuilder builder = new SpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                                 .init(new ReleaseIocFactory())
                                 .setupParser(new Script1(),
                                         new FrameStateMachineScriptParser.OnParsingFrameListener() {
                                             @Override
                                             public BiConsumer<AppStateWorld, Sprite> onParsing(FrameSegment segment) {
                                                 return null;
                                             }
                                         })
                                 .setupParser(new Script1())
                                 .setPropertiesComponent(new PropertiesComponent())
                                 .addComponent("collidable", new CollidableComponent())
                                 .build();

    }

    private Sprite sprite;
    private FrameStateMachineComponent fsmComponent;
    private PropertiesComponent propertiesComponent;
    FrameStateMachineScriptParser parser;

    /**
     * The constructor of SpriteBuilder.
     * @param iocFactory To do dependency injection.
     */
    public SpriteBuilder(IocFactory iocFactory) {
        sprite = null;
        fsmComponent = null;
        propertiesComponent = null;
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
        return this;
    }

    /**
     * Initialize before creating a new sprite, with a new parser.
     * @param iocFactory The provider of new parser.
     * @return The current builder.
     */
    public SpriteBuilder init(IocFactory iocFactory) {
        sprite = null;
        fsmComponent = null;
        propertiesComponent = null;
        parser = iocFactory.frameStateMachineScriptParser();
        return this;
    }


    /**
     * Setup the parser to generate FSM component, with listener provided.
     * @param script The script to generate FSM.
     * @param listener Client-defined parsing rules.
     * @return The current builder.
     */
    public SpriteBuilder setupParser(Script script,
                                     FrameStateMachineScriptParser.OnParsingFrameListener listener) {
        fsmComponent = parser.parse(script, listener);
        prepareSprite();
        return this;
    }

    /**
     * Setup the parser to generate FSM component, with default listener.
     * @param script The script ot generate FSM.
     * @return The current builder.
     */
    public SpriteBuilder setupParser(Script script) {
        fsmComponent = parser.parse(script);
        prepareSprite();
        return this;
    }

    /**
     * Let the client provide FSM component directly.
     * @param fsmComponent the FSM for the sprite.
     * @return The current builder.
     */
    public SpriteBuilder setFSMComponent(FrameStateMachineComponent fsmComponent) {
        this.fsmComponent = fsmComponent;
        prepareSprite();
        return this;
    }

    /**
     * Add Properties Component to the current sprite.
     * @param propertiesComponent The Properties Component required to define FSM.
     * @return The current builder.
     */
    public SpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
        prepareSprite();
        return this;
    }

    public SpriteBuilder addComponent(String name, Component component) {

        checkSpriteIsReady();

        if (sprite.getComponentByName(name).isPresent()) {
            throw new DuplicateComponentNameException("Duplicate component name is not allowed.");
        }

        sprite.putComponent(name, component);
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
                    "FrameStateMachineComponent is required, " +
                            "use setupParser() to create it.");
        }
        if (propertiesComponent == null) {
            throw new PropertiesComponentIsRequiredException(
                    "PropertiesComponent is required.");
        }
    }

}
