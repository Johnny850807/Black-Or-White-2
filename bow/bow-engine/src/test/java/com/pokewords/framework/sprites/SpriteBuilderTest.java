package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.MockComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
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
        PropertiesComponent propertiesComponent = new PropertiesComponent("TYPE");
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();
        MockComponent mockComponent = new MockComponent();

        Sprite sprite = spriteBuilder.init()
                    .setPropertiesComponent(propertiesComponent)
                    .setFSMComponent(fsmc)
                    .addComponent(MOCK, new MockComponent())
                    .build();

        assertSame(propertiesComponent, sprite.getPropertiesComponent());
        assertSame(fsmc, sprite.getFrameStateMachineComponent());
        assertSame(mockComponent, sprite.getComponentByName(MOCK));
    }

}
