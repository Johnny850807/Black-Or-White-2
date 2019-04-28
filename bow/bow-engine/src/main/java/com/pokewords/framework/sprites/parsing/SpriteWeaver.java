package com.pokewords.framework.sprites.parsing;
import com.pokewords.framework.sprites.Sprite;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public class SpriteWeaver {

    private LinkedList<BiConsumer<Script, Sprite>> weaverNodes;

    public SpriteWeaver() {
        weaverNodes = new LinkedList<>();
    }

    public void addNode(BiConsumer<Script, Sprite> node) {
        weaverNodes.add(node);
    }

    public void weave() {

    }
}
