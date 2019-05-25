package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.parsing.Script;

/**
 * @author johnny850807 (waterball)
 */
public class MockSpriteWeaverNode implements SpriteWeaver.Node {
    private Script weavingScript;
    private Sprite weavedSprite;

    @Override
    public void onWeaving(Script script, Sprite sprite, IocContainer iocContainer) {
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
