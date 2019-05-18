package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.Sprite;

/**
 * The MockDefaultSpriteBuilder records the latest built sprite with no weaver nodes as default.
 *
 * @see MockSpriteWeaverNode
 * @author johnny850807 (waterball)
 */
public class MockDefaultSpriteBuilder extends DefaultSpriteBuilder {
    private Sprite builtSprite;

    public MockDefaultSpriteBuilder(IocFactory iocFactory) {
        super(iocFactory);

        weaverNodes.clear();
    }

    @Override
    public Sprite build() {
        builtSprite = super.build();
        return builtSprite;
    }

    public Sprite getBuiltSprite() {
        return builtSprite;
    }
}
