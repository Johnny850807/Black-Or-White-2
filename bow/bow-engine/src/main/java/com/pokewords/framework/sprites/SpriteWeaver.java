package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.Script;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public class SpriteWeaver {
    private IocFactory iocFactory;
    private LinkedList<SpriteWeaver.Node> weaverNodes;

    public SpriteWeaver(IocFactory iocFactory) {
        this.iocFactory = iocFactory;
        init();
    }

    private void init() {
        weaverNodes = new LinkedList<Node>();
        weaverNodes.add(new GameEngineWeaverNode());
    }

    public void addWeaverNode(SpriteWeaver.Node node) {
        weaverNodes.add(node);
    }

    public void weave(Script script, Sprite sprite) {
        for (Node weaverNode : weaverNodes) {
            weaverNode.onWeaving(script, sprite);
        }
    }

    @FunctionalInterface
    public interface Node {
        /**
         * Each weaverNodes is responsible parsing their recognizable segments or elements.
         * @param sprite the sprite
         * @param script the sprite's declaration script.
         */
        void onWeaving(Script script, Sprite sprite);
    }
}