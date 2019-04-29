package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.MandatoryComponentIsRequiredException;
import com.pokewords.framework.engine.exceptions.ScriptException;
import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.engine.exceptions.DuplicateComponentNameException;
import com.pokewords.framework.ioc.IocFactory;

import java.util.List;
import java.util.function.BiConsumer;

/**
 *
 *   #2
 *   先做出Sprite
 *   再parse得出Script
 *   用Script完成fsmc
 *      Script傳給FSMBuilder製作出Frame完成FSM component?
 *   SpriteWeaver用(Script, Sprite)完成Sprite
 *      fsmc只是sprite的一部分
 *      SpriteWeaver完成sprite的所有部分
 *
 *
 * @author nyngwang
 */
public class SpriteBuilder {

    public static void main(String[] args) {

        SpriteBuilder builder = new SpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                                     .init(new ReleaseIocFactory())
                                 .setFSMComponent(new FrameStateMachineComponent())
                                 .setPropertiesComponent(new PropertiesComponent())
                                 .addComponent(Component.COLLIDABLE, new CollidableComponent())
                                 .buildScriptFromScriptTextPath("path/to/script_text")
                                     .buildScriptFromScriptText(FileUtility.read("path/to/script_text"))
                                     .setScript(null)
                                 .addWeaverNode((script, sprite) -> {
                                                    List<Element> bows = script.getSegmentById("frame")
                                                            .get().getElementsByName("bow");
                                                })
                                 .build();
    }

    private Sprite sprite;
    private Script.Parser scriptParser;
    private Script script;
    private SpriteWeaver spriteWeaver;
    private FrameFactory frameFactory;

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
        sprite = new Sprite(new FrameStateMachineComponent(), new PropertiesComponent());
        script = null;
        spriteWeaver = new SpriteWeaver();
        return this;
    }

    /**
     * Initialize sprite, mandatory components and script-parser.
     * @param iocFactory The provider of new scriptParser.
     * @return The current builder.
     */
    public SpriteBuilder init(IocFactory iocFactory) {
        scriptParser = iocFactory.scriptParser();
        frameFactory = iocFactory.frameFactory();
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
        script = scriptParser.parse(scriptText);
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

    /**
     * Add component to sprite.
     * @param name component name
     * @param component component
     * @return The current builder.
     */
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
        validateScript();
        // 啟動Weaver
        spriteWeaver.weave();
        ComponentInjector.inject(sprite);
        return sprite;
    }

    private void validateScript() {
        if (script == null) {
            throw new ScriptException(
                    "Script is required, use setScript(), " +
                    "buildScriptFromScriptText(), " +
                    "buildScriptFromScriptTextPath() " +
                    "to setup script."
            );
        }
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
