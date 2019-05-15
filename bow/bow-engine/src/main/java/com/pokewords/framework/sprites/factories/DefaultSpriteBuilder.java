package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.engine.GameEngineWeaverNode;
import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.engine.utils.FileUtility;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.ioc.IocFactory;

import java.io.IOException;
import java.util.*;

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
                                 .addComponent(new CollidableComponent())
                                 .buildScriptFromPath("path/to/script_text")
                                 .setScript(null)
                                 .addWeaverNode((script, sprite) -> {
                                     List<Element> bows = script.getSegmentById("frame").getElementsByName("bow");
                                 })
                                 .build();
    }


    protected Sprite sprite;
    protected Set<Component> components;
    protected boolean hasPropertiesComponent;
    protected PropertiesComponent propertiesComponent;
    protected Script script;
    protected SpriteWeaver spriteWeaver;
    protected ScriptParser scriptParser;
    protected ScriptRulesParser scriptRulesParser;
    protected List<SpriteWeaver.Node> defaultWeaverNodes = new LinkedList<SpriteWeaver.Node>() {
        {
            add(new GameEngineWeaverNode());
        }
    };


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
        components = new HashSet<>();
        hasPropertiesComponent = false;
        return this;
    }

    @Override
    public DefaultSpriteBuilder setFSMComponent(FrameStateMachineComponent frameStateMachineComponent) {
        components.add(frameStateMachineComponent);
        return this;
    }

    @Override
    public DefaultSpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent) {
        components.add(propertiesComponent);
        hasPropertiesComponent = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder addComponent(Component component) {
        components.add(component);
        if (component instanceof PropertiesComponent)
            hasPropertiesComponent = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder buildScriptFromPath(String path) {
        try {
            script = scriptParser.parse(FileUtility.read(path),
                     ScriptDefinitions.LinScript.Samples.SCRIPT_RULES);
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
        setupAndStartSpriteWeaving();
        return sprite;
    }

    private void checkScript() {
        if (script == null) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: Script hasn't been set.");
        }
    }

    private void setupSprite() {
        if (!hasPropertiesComponent) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: PropertiesComponent is not set.");
        }
        sprite = new Sprite(components);
    }

    private void setupAndStartSpriteWeaving() {
        spriteWeaver.addWeaverNodes(defaultWeaverNodes);
        spriteWeaver.weave(script, sprite);
    }
}
