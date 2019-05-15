package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.mocks.MockComponentImp;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.LinScript;
import com.pokewords.framework.sprites.parsing.LinScriptSegment;
import com.pokewords.framework.sprites.parsing.Script;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class SpriteBuilderTest extends AbstractTest {
    MockDefaultSpriteBuilder mockDefaultSpriteBuilder;

    @Before
    public void setup(){
        mockDefaultSpriteBuilder = new MockDefaultSpriteBuilder(mock);
    }

    @Test
    public void testAddComponent() {
        PropertiesComponent propertiesComponent = new PropertiesComponent("TYPE");
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();
        CollidableComponent collidableComponent = CollidableComponent.ignoreTypes("Ignored");
        MockComponentImp mockComponent = new MockComponentImp();

        Sprite sprite = mockDefaultSpriteBuilder.init()
                    .setPropertiesComponent(propertiesComponent)
                    .addComponent(collidableComponent)
                    .addComponent(mockComponent)
                    .setFSMComponent(fsmc)
                    .build();

        assertSame(propertiesComponent, sprite.getPropertiesComponent());
        assertSame(collidableComponent, sprite.getCollidableComponent());
        assertSame(fsmc, sprite.getFrameStateMachineComponent());
        assertSame(mockComponent, sprite.getComponent(MockComponentImp.class));
    }

    @Test
    public void testWeavingFromLinScript() {
        LinScript script = new LinScript();
        MockSpriteWeaverNode mockSpriteWeaverNode = new MockSpriteWeaverNode();
        PropertiesComponent propertiesComponent = new PropertiesComponent("type");
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
        mockDefaultSpriteBuilder.addComponent(new CollidableComponent())
                            .addComponent(new FrameStateMachineComponent())
                            .addWeaverNode(new MockSpriteWeaverNode())
                            .build();
    }

}
