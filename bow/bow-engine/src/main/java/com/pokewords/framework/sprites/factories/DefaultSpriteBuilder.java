package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.engine.weaver.GameEngineWeaverNode;
import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.ioc.IocFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * TODO: Should re-implement.
 * @author nyngwang
 */
public class DefaultSpriteBuilder implements SpriteBuilder {
    protected Sprite sprite;
    protected Set<Component> components;
    protected boolean hasPropertiesComponent;
    protected Script script;
    protected SpriteWeaver spriteWeaver;
    protected List<SpriteWeaver.Node> weaverNodes = new LinkedList<>();

    public DefaultSpriteBuilder(IocFactory iocFactory) {
        spriteWeaver = new SpriteWeaver(iocFactory);
        components = new HashSet<>();
        init();
    }

    @Override
    public DefaultSpriteBuilder init() {
        sprite = null;
        script = null;
        components.clear();
        hasPropertiesComponent = false;
        spriteWeaver.clear();
        weaverNodes = new LinkedList<>(Collections.singletonList(new GameEngineWeaverNode()));
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
            String script = new String(Files.readAllBytes(Resources.get(path).toPath())).trim();
            this.script = new LinScript();
            this.script.parse(Context.fromText(script));
            addComponent(new FrameStateMachineComponent());
        } catch (IOException e) {
            e.printStackTrace();
            init();
        }
        return this;
    }

    @Override
    public DefaultSpriteBuilder setScript(@NotNull Script script) {
        this.script = script;
        addComponent(new FrameStateMachineComponent());
        return this;
    }

    @Override
    public DefaultSpriteBuilder addWeaverNode(SpriteWeaver.Node node) {
        weaverNodes.add(node);
        return this;
    }

    @Override
    public Sprite build() {
        setupSprite();
        setupAndStartSpriteWeaving();
        return sprite;
    }


    private void setupSprite() {
        if (!hasPropertiesComponent) {
            throw new SpriteBuilderException("DefaultSpriteBuilder: PropertiesComponent is not set.");
        }
        sprite = new Sprite(components);
    }

    private void setupAndStartSpriteWeaving() {
        if (script != null)
        {
            spriteWeaver.addWeaverNodes(weaverNodes);
            spriteWeaver.weave(script, sprite);
        }
    }


    public static void main(String[] args) {

        DefaultSpriteBuilder builder = new DefaultSpriteBuilder(new ReleaseIocFactory());

        Sprite mySprite = builder.init()
                .setFSMComponent(new FrameStateMachineComponent())
                .setPropertiesComponent(new PropertiesComponent())
                .addComponent(new CollidableComponent())
                .buildScriptFromPath("path/to/script_text")
                .addWeaverNode((script, sprite) -> {
                    for (Segment frameSegment: script.getSegments("frame")) {
                        List<Element> bows = frameSegment.getElements("bow");
                    }
                })
                .build();
    }
}
