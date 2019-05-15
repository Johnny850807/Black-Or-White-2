package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.Sprite;

/**
 * The MockSpriteBuilder records the latest built sprite.
 * @author johnny850807 (waterball)
 */
public class MockSpriteBuilder extends DefaultSpriteBuilder {
    private Sprite builtSprite;

    public MockSpriteBuilder(IocFactory iocFactory) {
        super(iocFactory);
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
