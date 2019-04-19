package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubUtility;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.Shareable;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class SpriteTest extends AbstractTest {
    Sprite spriteStub;

    @Before
    public void setup(){
        spriteStub = StubUtility.Sprites.createSpriteStub();
    }

    @Test
    public void testGetAllShareableComponents(){
        Set<Component> components = spriteStub.getShareableComponents();
        for (Component component : components) {
            assertTrue(component instanceof Shareable);
        }
    }

    @Test
    public void testGetAllNonshareableComponents(){
        Set<Component> components = spriteStub.getNonshareableComponents();
        for (Component component : components) {
            assertFalse(component instanceof Shareable);
        }
    }

    @Test
    public void testSpriteClone(){
        Sprite clone = spriteStub.clone();
        testSpriteFieldsCloned(spriteStub, clone);
    }

    private void testSpriteFieldsCloned(Sprite spriteStub, Sprite clone) {
        assertNotSameButEquals(spriteStub, clone);
        assertNotSameButEquals(spriteStub.getComponents(), clone.getComponents());
        assertNotSameButEquals(spriteStub.getPropertiesComponent(), clone.getPropertiesComponent());

        // all sprites of the same type must share the FSMC !
        assertSame(spriteStub.getFrameStateMachineComponent(), clone.getFrameStateMachineComponent());

        testComponentsShareability(spriteStub.getComponents(), clone.getComponents());
    }

    private void testComponentsShareability(Map<String, Component> stubComponents, Map<String, Component> clonedComponents) {
        for (String name : stubComponents.keySet()) {
            Component stubComponent = stubComponents.get(name);
            Component cloneComponent = clonedComponents.get(name);
            if (stubComponent instanceof Shareable)
                assertSame(stubComponent, cloneComponent);
            else
                assertNotSameButEquals(stubComponent, cloneComponent);
        }
    }


}
