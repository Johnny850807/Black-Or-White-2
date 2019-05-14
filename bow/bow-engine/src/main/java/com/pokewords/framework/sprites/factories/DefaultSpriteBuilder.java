package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.Sprite;
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
 *   2. 再 LinScript.Parser.parse() 得出 LinScript
 *   3. SpriteWeaver用(LinScript, Sprite)完成Sprite
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


    private Sprite sprite;
    private Map<String, Component> nameToComponent;
    private boolean hasPropertiesComponent;
    private Script script;
    private SpriteWeaver spriteWeaver;
    private ScriptParser scriptParser;
    private ScriptRulesParser scriptRulesParser;


    public DefaultSpriteBuilder(IocFactory iocFactory) {
        init();
        script = new LinScript();
        spriteWeaver = new SpriteWeaver(iocFactory);
        scriptParser = iocFactory.scriptParser();
        scriptRulesParser = iocFactory.scriptRulesParser();
    }

    @Override
    public DefaultSpriteBuilder init() {
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
        nameToComponent.put(name, component);
        if (component instanceof PropertiesComponent)
            hasPropertiesComponent = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder buildScriptFromPath(String path) {
        try {
            script = scriptParser.parse(FileUtility.read(path),
                    scriptRulesParser.parse(ScriptDefinitions.LinScript.Samples.SCRIPT_RULES_TEXT));
        } catch (IOException e) {
            e.printStackTrace();
            init();
        }
        return this;
    }

    @Override
    public DefaultSpriteBuilder setScript(Script script) {
        this.script = script;
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
        if (script == null) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: LinScript is not set.");
        }
    }

    private void setupSprite() {
        if (!hasPropertiesComponent) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: PropertiesComponent is not set.");
        }
        sprite = new Sprite((PropertiesComponent) nameToComponent.get(Component.PROPERTIES));
        for (Map.Entry<String, Component> entry : nameToComponent.entrySet()) {
            sprite.addComponent(entry.getKey(), entry.getValue());
        }
    }

    private void startWeaver() {
        spriteWeaver.weave(script, sprite);
    }
}
