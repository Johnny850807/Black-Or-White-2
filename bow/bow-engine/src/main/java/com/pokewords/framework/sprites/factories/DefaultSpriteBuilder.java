package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.engine.weaver.GameEngineWeaverNode;
import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.parsing.*;
import com.pokewords.framework.ioc.IocContainer;
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

    /**
     * Use this boolean to indicate if init() has been invoked. If the client forgot to invoke init()
     * after the last build() but invokes other building method, exception thrown.
     */
    protected boolean hasBeenInit = false;

    public DefaultSpriteBuilder(IocContainer iocContainer) {
        spriteWeaver = new SpriteWeaver(iocContainer);
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
        hasBeenInit = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder setFSMComponent(FrameStateMachineComponent frameStateMachineComponent) {
        validateIfInitHasBeenInvoked();
        components.add(frameStateMachineComponent);
        return this;
    }

    @Override
    public DefaultSpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent) {
        validateIfInitHasBeenInvoked();

        components.add(propertiesComponent);
        hasPropertiesComponent = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder addComponent(Component component) {
        validateIfInitHasBeenInvoked();

        components.add(component);
        if (component instanceof PropertiesComponent)
            hasPropertiesComponent = true;
        return this;
    }

    @Override
    public DefaultSpriteBuilder buildScriptFromPath(String path) {
        validateIfInitHasBeenInvoked();

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
        validateIfInitHasBeenInvoked();

        this.script = script;
        addComponent(new FrameStateMachineComponent());
        return this;
    }

    @Override
    public DefaultSpriteBuilder addWeaverNode(SpriteWeaver.Node node) {
        validateIfInitHasBeenInvoked();
        weaverNodes.add(node);
        return this;
    }

    @Override
    public Sprite build() {
        validateIfInitHasBeenInvoked();
        setupSprite();
        setupAndStartSpriteWeaving();
        hasBeenInit = false;
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

    private void validateIfInitHasBeenInvoked() {
        if (!hasBeenInit)
            throw new IllegalStateException("SpriteBuilder's init() should be invoked before using any building methods.");
    }

    public static void main(String[] args) {

        DefaultSpriteBuilder builder = new DefaultSpriteBuilder(new ReleaseIocContainer());

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
