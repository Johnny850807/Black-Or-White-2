package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubUtility;
import com.pokewords.framework.sprites.components.*;
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

    @Test
    public void testGetRenderableComponents() {
        Sprite sprite = StubUtility.Sprites.createSpriteStub();
        MockRenderableComponent r1 = new MockRenderableComponent();
        MockFrame rf1 = new MockFrame("rf1");
        r1.addFrame(rf1);

        MockRenderableComponent r2 = new MockRenderableComponent();
        MockFrame rf2 = new MockFrame("rf2");
        r1.addFrame(rf2);

        MockRenderableComponent r3 = new MockRenderableComponent();
        MockFrame rf3 = new MockFrame("rf3");
        r1.addFrame(rf3);

        sprite.putComponent("r1", r1);
        sprite.putComponent("r2", r2);
        sprite.putComponent("r3", r3);

        Set<Frame> currentFrames = sprite.getCurrentFrames();
        assertTrue(currentFrames.contains(StubUtility.FSCM.FRAME_A));  //the initial state of the stub should be contained
        assertTrue(currentFrames.contains(rf1));
        assertTrue(currentFrames.contains(rf2));
        assertTrue(currentFrames.contains(rf3));
    }

    @Test
    public void testSpriteEqualsAndHashCode() {
        Sprite sp1 = new Sprite();
        Sprite sp2 = new Sprite();
        sp1.setType("type");
        sp1.setBody(50, 50, 50, 50);
        sp2.setBody(50, 50, 50, 50);
        sp2.setType("type");
        assertEquals(sp1.getPropertiesComponent(), sp2.getPropertiesComponent());
        assertEquals(sp1.hashCode(), sp2.hashCode());
    }

}
