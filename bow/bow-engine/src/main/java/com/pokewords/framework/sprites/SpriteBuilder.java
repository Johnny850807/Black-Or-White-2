package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
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
public class SpriteBuilder {

    public static void main(String[] args) {

        SpriteBuilder builder = new SpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                                     .setIocFactory(new ReleaseIocFactory())
                                 .setFSMComponent(new FrameStateMachineComponent())
                                 .setPropertiesComponent(new PropertiesComponent())
                                 .addComponent(Component.COLLIDABLE, new CollidableComponent())
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


    public SpriteBuilder(IocFactory iocFactory) {
        init();
        spriteWeaver = new SpriteWeaver(iocFactory, sprite);
    }

    public SpriteBuilder setIocFactory(IocFactory iocFactory) {
        spriteWeaver.setIocFactory(iocFactory);
        return this;
    }

    public SpriteBuilder init() {
        sprite = new Sprite(new FrameStateMachineComponent(), new PropertiesComponent());
        script = null;
        return this;
    }

    public SpriteBuilder setFSMComponent(FrameStateMachineComponent fsmComponent) {
        spriteWeaver.setFSMComponent(fsmComponent);
        return this;
    }

    public SpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent) {
        spriteWeaver.setPropertiesComponent(propertiesComponent);
        return this;
    }

    public SpriteBuilder addComponent(String name, Component component) {
        spriteWeaver.addComponent(name, component);
        return this;
    }

    public SpriteBuilder buildScriptFromPath(String path) {
        script = Script.Parser.parse(FileUtility.read(path),
                 Script.Rules.Parser.parse(Script.Rules.Def.DEFAULT_RULES_TEXT));
        return this;
    }

    public SpriteBuilder setScript(Script script) {
        this.script = script;
        return this;
    }

    public SpriteBuilder addWeaverNode(BiConsumer<Script, Sprite> node) {
        spriteWeaver.addNode(node);
        return this;
    }

    public Sprite build() {
        spriteWeaver.weave();
        ComponentInjector.inject(sprite);
        return sprite;
    }

}
