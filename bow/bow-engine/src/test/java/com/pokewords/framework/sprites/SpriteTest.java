package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubUtility;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.Shareable;
import org.junit.Before;
import org.junit.Test;

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
        Set<Component> components = spriteStub.getShareableComponents();
        for (Component component : components) {
            assertFalse(component instanceof Shareable);
        }
    }

    @Test
    public void testSpriteClone(){
        Sprite clone = spriteStub.clone();
        testSpriteFieldsCloned(spriteStub, clone);
        testSpriteComponentsInjectedCorrected(clone);
    }

    private void testSpriteFieldsCloned(Sprite spriteStub, Sprite clone) {
        assertNotSame(spriteStub, clone);
        assertNotSame(spriteStub.getComponents(), clone.getComponents());
        assertNotSame(spriteStub.getPropertiesComponent(), clone.getPropertiesComponent());

        // all sprites of the same type should share the FSMC !
        assertSame(spriteStub.getFrameStateMachineComponent(), clone.getFrameStateMachineComponent());
    }

    private void testSpriteComponentsInjectedCorrected(Sprite clone) {

    }
}
