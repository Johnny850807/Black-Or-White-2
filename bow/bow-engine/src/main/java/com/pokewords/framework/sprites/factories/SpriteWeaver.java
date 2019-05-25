package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.parsing.Script;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Chain of responsibility. Each node within the weaver is interested in parsing certain parts of Script.
 * @author nyngwang
 */
public class SpriteWeaver {
    private IocContainer iocContainer;
    private LinkedList<SpriteWeaver.Node> weaverNodes;

    public SpriteWeaver(IocContainer iocContainer) {
        weaverNodes = new LinkedList<>();
        this.iocContainer = iocContainer;
    }

    public void addWeaverNode(SpriteWeaver.Node node) {
        weaverNodes.add(node);
    }

    public void addWeaverNodes(Collection<Node> nodes) {
        weaverNodes.addAll(nodes);
    }

    public void removeWeaverNode(SpriteWeaver.Node node) {
        weaverNodes.remove(node);
    }

    public void clear() {
        weaverNodes.clear();
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
