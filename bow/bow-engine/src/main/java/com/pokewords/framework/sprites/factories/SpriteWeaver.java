package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.engine.GameEngineWeaverNode;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.parsing.Script;

import java.util.LinkedList;

public class SpriteWeaver {
    private IocFactory iocFactory;
    private LinkedList<SpriteWeaver.Node> weaverNodes;

    public SpriteWeaver(IocFactory iocFactory) {
        init();
        this.iocFactory = iocFactory;
    }

    private void init() {
        weaverNodes = new LinkedList<>();
    }

    public void addWeaverNode(SpriteWeaver.Node node) {
        weaverNodes.add(node);
    }

    public void removeWeaverNode(SpriteWeaver.Node node) {
        weaverNodes.remove(node);
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
