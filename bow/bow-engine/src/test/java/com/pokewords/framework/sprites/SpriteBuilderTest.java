package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.sprites.components.MockComponent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class SpriteBuilderTest extends AbstractTest {
    final String MOCK = "mock";
    SpriteBuilder spriteBuilder;

    @Before
    public void setup(){
        spriteBuilder = new SpriteBuilder(mock);
    }

    @Test
    public void testAddComponent() {
        MockComponent mockComponent = new MockComponent();

        Sprite sprite = spriteBuilder.init()
                    .addComponent(MOCK, new MockComponent())
                    .build();

        assertSame(mockComponent, sprite.getComponentByName(MOCK));
    }

}
