package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.MandatoryComponentIsRequiredException;
import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.function.BiConsumer;

/**
 * Script 設定完後要做什麼？
 * @author nyngwang
 */
public class SpriteBuilder {

    public static void main(String[] args) {

        SpriteBuilder builder = new SpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                                     .init(new ReleaseIocFactory())
                                 .buildScriptFromScriptTextPath("path/to/scrip_text")
                                     .buildScriptFromScriptText(FileUtility.read("path/to/scrip_text"))
                                 .setScript(new LinScript(FileUtility.read("path/to/script_text")))
                                 .addWeaverNode((script, sprite) -> {
                                                    Element bow = script.getFrameSegment()
                                                                        .getElement("bow");
                                                })
                                 .setPropertiesComponent(new PropertiesComponent())
                                 .addComponent(Component.COLLIDABLE, new CollidableComponent())
                                 .build();
    }

    private Sprite sprite;
    private FrameStateMachineComponent fsmComponent;
    private PropertiesComponent propertiesComponent;
    private ScriptTextParser scriptTextParser;
    private Script script;
    private SpriteWeaver spriteWeaver;

    /**
     * The constructor of SpriteBuilder.
     * @param iocFactory To do dependency injection.
     */
    public SpriteBuilder(IocFactory iocFactory) {
        init(iocFactory);
    }

    /**
     * Initialize sprite and mandatory components.
     * @return The current builder.
     */
    public SpriteBuilder init() {
        sprite = null;
        fsmComponent = null;
        propertiesComponent = new PropertiesComponent();
        spriteWeaver = new SpriteWeaver();
        return this;
    }

    /**
     * Initialize sprite, mandatory components and script-parser.
     * @param iocFactory The provider of new scriptTextParser.
     * @return The current builder.
     */
    public SpriteBuilder init(IocFactory iocFactory) {
        scriptTextParser = iocFactory.scriptTextParser();
        return init();
    }

    /**
     * Set the script directly
     * @param script for the sprite
     * @return The current builder.
     */
    public SpriteBuilder setScript(Script script) {
        this.script = script;
        return this;
    }

    /**
     * Build script object by the script-text loaded from the path.
     * @param pathToScriptText path to the script-text.
     * @return The current builder.
     */
    public SpriteBuilder buildScriptFromScriptTextPath(String pathToScriptText) {
        return buildScriptFromScriptText(FileUtility.read(pathToScriptText));
    }

    /**
     * Build script object by the script-text.
     * @param scriptText the script-text
     * @return The current builder.
     */
    public SpriteBuilder buildScriptFromScriptText(String scriptText) {
        script = scriptTextParser.parse(scriptText);
        return this;
    }

    /**
     * Add weaver-node for sprite weaver.
     * @param node lambda to setup sprite by the script
     * @return The current builder.
     */
    public SpriteBuilder addWeaverNode(BiConsumer<Script, Sprite> node) {
        spriteWeaver.addNode(node);
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
