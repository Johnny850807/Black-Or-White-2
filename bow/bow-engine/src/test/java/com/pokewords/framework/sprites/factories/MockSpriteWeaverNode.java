package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.parsing.Script;

public class MockSpriteWeaverNode implements SpriteWeaver.Node {
    private Script weavingScript;
    private Sprite weavedSprite;

    @Override
    public void onWeaving(Script script, Sprite sprite) {
        this.weavingScript = script;
        this.weavedSprite = sprite;
    }

    public Script getWeavingScript() {
        return weavingScript;
    }

    public Sprite getWeavedSprite() {
        return weavedSprite;
    }
}
