package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.ioc.IocFactory;

import java.util.List;
import java.util.function.BiConsumer;

/**
 *
 *   1. 先做出 Sprite
 *   2. 再 Script.Parser.parse() 得出 Script
 *   3. SpriteWeaver用(Script, Sprite)完成Sprite
 *   TODO:
 *      - 包含處理 FSM Component
 *      - 及 Sprite 的其他部分
 *
 * @author nyngwang
 */
public class DefaultSpriteBuilder implements SpriteBuilder {

    public static void main(String[] args) {

        DefaultSpriteBuilder builder = new DefaultSpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                            .setIocFactory(new ReleaseIocFactory())
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

    private Sprite sprite;
    private SpriteWeaver spriteWeaver;
    private Script script;


    public DefaultSpriteBuilder(IocFactory iocFactory) {
        init();
        spriteWeaver = new SpriteWeaver(iocFactory, sprite);
    }

    public DefaultSpriteBuilder setIocFactory(IocFactory iocFactory) {
        spriteWeaver.setIocFactory(iocFactory);
        return this;
    }

    @Override
    public DefaultSpriteBuilder init() {
        sprite = new Sprite(new FrameStateMachineComponent(), new PropertiesComponent());
        script = null;
        return this;
    }

    @Override
    public DefaultSpriteBuilder setFSMComponent(FrameStateMachineComponent fsmComponent) {
        spriteWeaver.setFSMComponent(fsmComponent);
        return this;
    }

    @Override
    public DefaultSpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent) {
        spriteWeaver.setPropertiesComponent(propertiesComponent);
        return this;
    }

    @Override
    public DefaultSpriteBuilder addComponent(String name, Component component) {
        spriteWeaver.addComponent(name, component);
        return this;
    }

    @Override
    public DefaultSpriteBuilder buildScriptFromPath(String path) {
        script = Script.Parser.parse(FileUtility.read(path),
                 ScriptSample.LinScript.RULES);
        return this;
    }

    @Override
    public DefaultSpriteBuilder setScript(Script script) {
        this.script = script;
        return this;
    }

    @Override
    public DefaultSpriteBuilder addWeaverNode(SpriteWeaver.Node node) {
        spriteWeaver.addNode(node);
        return this;
    }

    @Override
    public Sprite build() {
        spriteWeaver.weave();
        return sprite;
    }

}
