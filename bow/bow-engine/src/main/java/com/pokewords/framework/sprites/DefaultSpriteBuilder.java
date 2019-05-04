package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.ioc.IocFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *   1. 先做出 Sprite
 *   2. 再 Script.Parser.parse() 得出 Script
 *   3. SpriteWeaver用(Script, Sprite)完成Sprite
 *   TODO:
 *      -
 *
 * @author nyngwang
 */
public class DefaultSpriteBuilder implements SpriteBuilder {

    public static void main(String[] args) {

        DefaultSpriteBuilder builder = new DefaultSpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                                 .setFSMComponent(new FrameStateMachineComponent())
                                 .setPropertiesComponent(new PropertiesComponent())
                                 .addComponent(Component.COLLIDABLE, CollidableComponent.getInstance())
                                 .buildScriptFromPath("path/to/script_text")
                                 .setScript(null)
                                 .addWeaverNode((script, sprite) -> {
                                     List<Element> bows = script.getSegmentById("frame")
                                             .get().getElementsByName("bow");
                                 })
                                 .build();
    }

    private IocFactory iocFactory; // for spriteWeaver constructor.
    private Script script;
    private boolean hasScript;
    private Sprite sprite;
    private Map<String, Component> nameToComponent;
    private boolean hasPropertiesComponent;
    private SpriteWeaver spriteWeaver;


    public DefaultSpriteBuilder(IocFactory iocFactory) {
        this.iocFactory = iocFactory;
        init();
    }

    @Override
    public DefaultSpriteBuilder init() {
        script = null;
        hasScript = false;
        sprite = null;
        nameToComponent = new HashMap<>();
        hasPropertiesComponent = false;
        return this;
    }

    @Override
    public DefaultSpriteBuilder setFSMComponent(FrameStateMachineComponent frameStateMachineComponent) {
        nameToComponent.put(Component.FRAME_STATE_MACHINE, frameStateMachineComponent);
        return this;
    }

    @Override
    public DefaultSpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent) {
        nameToComponent.put(Component.PROPERTIES, propertiesComponent);
        hasPropertiesComponent = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder addComponent(String name, Component component) {
        sprite.putComponent(name, component);
        hasPropertiesComponent = component instanceof PropertiesComponent;
        return this;
    }

    @Override
    public DefaultSpriteBuilder buildScriptFromPath(String path) {
        try {
            script = Script.Parser.parse(FileUtility.read(path),
                     ScriptSample.LinScript.RULES);
            hasScript = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public DefaultSpriteBuilder setScript(Script script) {
        this.script = script;
        hasScript = script != null;
        return this;
    }

    @Override
    public DefaultSpriteBuilder addWeaverNode(SpriteWeaver.Node node) {
        spriteWeaver.addWeaverNode(node);
        return this;
    }

    @Override
    public Sprite build() {
        checkScript();
        setupSprite();
        startWeaver();
        return sprite;
    }

    private void checkScript() {
        if (!hasScript) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: Script is not set.");
        }
    }

    private void setupSprite() {
        if (!hasPropertiesComponent) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: PropertiesComponent is not set.");
        }
        for (Map.Entry<String, Component> entry : nameToComponent.entrySet()) {
            sprite.putComponent(entry.getKey(), entry.getValue());
        }
    }

    private void startWeaver() {
        spriteWeaver = new SpriteWeaver(iocFactory);
        spriteWeaver.weave(script, sprite);
    }
}
