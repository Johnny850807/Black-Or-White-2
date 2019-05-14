package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.mocks.MockComponentImp;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class SpriteBuilderTest extends AbstractTest {
    final String MOCK = "mock";
    DefaultSpriteBuilder spriteBuilder;

    @Before
    public void setup(){
        spriteBuilder = new DefaultSpriteBuilder(mock);
    }

    @Test
    public void testAddComponent() {
        PropertiesComponent propertiesComponent = new PropertiesComponent("TYPE");
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();
        CollidableComponent collidableComponent = CollidableComponent.ignoreTypes("Ignored");
        MockComponentImp mockComponent = new MockComponentImp();

        Sprite sprite = spriteBuilder.init()
                    .setPropertiesComponent(propertiesComponent)
                    .addComponent(MOCK, mockComponent)
                    .setFSMComponent(fsmc)
                    .build();

        assertSame(propertiesComponent, sprite.getPropertiesComponent());
        assertSame(collidableComponent, sprite.getCollidableComponent());
        assertSame(fsmc, sprite.getFrameStateMachineComponent());
        assertSame(mockComponent, sprite.getComponent(MockComponentImp.class));
    }

}
