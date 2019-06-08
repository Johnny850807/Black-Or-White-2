package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.mocks.MockComponentImp;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.LinScript;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author johnny850807 (waterball)
 */
public class SpriteBuilderTest extends AbstractTest {
    private MockDefaultSpriteBuilder mockDefaultSpriteBuilder;

    @Before
    public void setup(){
        mockDefaultSpriteBuilder = new MockDefaultSpriteBuilder(release);
    }

    @Test
    public void testEmptySpriteBuilt() {
        PropertiesComponent propertiesComponent = new PropertiesComponent("concreteType");
        Sprite sprite = mockDefaultSpriteBuilder.setPropertiesComponent(propertiesComponent).build();

        assertEquals(1,sprite.getComponents().size());
        assertSame(propertiesComponent,sprite.getPropertiesComponent());
    }

    @Test
    public void testAddComponent() {
        PropertiesComponent propertiesComponent = new PropertiesComponent("TYPE");
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();
        MockComponentImp mockComponent = new MockComponentImp();

        Sprite sprite = mockDefaultSpriteBuilder.init()
                    .setPropertiesComponent(propertiesComponent)
                    .addComponent(mockComponent)
                    .setFSMComponent(fsmc)
                    .build();

        assertSame(propertiesComponent, sprite.getPropertiesComponent());
        assertSame(fsmc, sprite.getFrameStateMachineComponent());
        assertSame(mockComponent, sprite.getComponent(MockComponentImp.class));
    }

    @Test
    public void testWeavingFromLinScript() {
        LinScript script = new LinScript();
        MockSpriteWeaverNode mockSpriteWeaverNode = new MockSpriteWeaverNode();
        PropertiesComponent propertiesComponent = new PropertiesComponent("concreteType");
        Sprite sprite = mockDefaultSpriteBuilder.addWeaverNode(mockSpriteWeaverNode)
                                .setPropertiesComponent(propertiesComponent)
                                .setScript(script)
                                .build();

        assertSame(propertiesComponent, sprite.getComponent(PropertiesComponent.class));
        assertSame(mockSpriteWeaverNode.getWeavingScript(), script);
        assertSame(mockSpriteWeaverNode.getWeavedSprite(), sprite);
    }

    @Test(expected = GameEngineException.class)
    public void testShouldThrowExceptionIfNoSetPropertiesComponent() {
        mockDefaultSpriteBuilder.addComponent(new FrameStateMachineComponent())
                            .addWeaverNode(new MockSpriteWeaverNode())
                            .build();
    }


}
