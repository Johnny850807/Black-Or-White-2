package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.MandatoryComponentIsRequiredException;
import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.FrameStateMachineScriptParser;
import com.pokewords.framework.sprites.parsing.LinScript;

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
 *
 *
 * @author nyngwang
 */
public class SpriteBuilder {

    public static void main(String[] args) {
        SpriteBuilder builder = new SpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                                 .init(new ReleaseIocFactory())
                                 .setupParser("path/to/script",
                                         frameSegment -> {
                                            // parse client's own elements
                                            return (world, sprite) -> {
                                                // return some customized action during the frame is applied to the world
                                            };
                                         })
                                 .setupParser(new LinScript(FileUtility.read("my_home")))
                                 .setPropertiesComponent(new PropertiesComponent())
                                 .addComponent(Component.COLLIDABLE, new CollidableComponent())
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
        init(iocFactory);
    }

    /**
     * Initialize before creating a new sprite.
     * @return The current builder.
     */
    public SpriteBuilder init() {
        sprite = null;
        fsmComponent = null;
        propertiesComponent = new PropertiesComponent();
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
        propertiesComponent = new PropertiesComponent();
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
     * Setup the parser to generate FSM component, with listener provided.
     * @param path The file path of the script file.
     * @param listener Client-defined parsing rules.
     * @return The current builder.
     */
    public SpriteBuilder setupParser(String path,
                                     FrameStateMachineScriptParser.OnParsingFrameListener listener) {
        LinScript script = new LinScript(FileUtility.read(path));
        return setupParser(script, listener);
    }

    /**
     * Setup the parser to generate FSM component, with listener provided.
     * @param path The file path of the script file.
     * @return The current builder.
     */
    public SpriteBuilder setupParser(String path) {
        LinScript script = new LinScript(FileUtility.read(path));
        return setupParser(script);
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

        validateSpriteMandatoryComponents();

        if (sprite.getComponentByName(name).isPresent()) {
            throw new DuplicateComponentNameException("Duplicate component name is not allowed.");
        }

        sprite.putComponent(name, component);

        return this;
    }

    /**
     * Return the built sprite.
     * @return The sprite.
     */
    public Sprite build() {
        validateSpriteMandatoryComponents();
        return sprite;
    }

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
     * @throws MandatoryComponentIsRequiredException if the mandatory components are not initiated
     */
    private void validateSpriteMandatoryComponents() {
        if (fsmComponent == null) {
            throw new MandatoryComponentIsRequiredException(
                    "FrameStateMachineComponent is required, use setupParser() to create it");
        }
        if (propertiesComponent == null) {
            throw new MandatoryComponentIsRequiredException(
                    "PropertiesComponent is required, use setPropertiesComponent() to create it");
        }
    }

}
