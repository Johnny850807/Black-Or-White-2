package com.pokewords.framework.sprites.components;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.commons.utils.StubFactory;
import com.pokewords.framework.sprites.components.mocks.MockEffectFrame;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author johnny850807 (waterball)
 */
public class ComponentsTest extends AbstractTest {


    @Test
    public void testPropertiesComponentAccessors() {
        PropertiesComponent pc = new PropertiesComponent("Type");
        pc.setBody(50, 50, 50, 50);
        pc.setCenter(200, 200);

        assertEquals("Type", pc.getType());
        assertEquals(50, pc.getX());
        assertEquals(50, pc.getY());
        assertEquals(50, pc.getWidth());
        assertEquals(50, pc.getHeight());
        assertEquals(new Rectangle(50, 50, 50, 50), pc.getBody());
        assertEquals(new Point(200, 200), pc.getCenter());

        pc.setBody(new Rectangle(100, 100, 100, 100));
        assertEquals(new Rectangle(100, 100, 100, 100), pc.getBody());

        pc.setPosition(20, 20);
        assertEquals(new Rectangle(20, 20, 100, 100), pc.getBody());

        pc.setCenter(new Point(300, 300));
        assertEquals(new Point(300, 300), pc.getCenter());

        pc.setType("Set-Type");
        assertEquals("Set-Type", pc.getType());
    }

    @Test
    public void testPropertiesComponentEqualsAndHashcode() {
        PropertiesComponent p1 = givenPropertiesComponent();
        PropertiesComponent p2 = givenPropertiesComponent();
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        // after p2's been changed, they should no longer be equal
        p2.setPosition(p1.getX()+1, p1.getY()+1);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    private PropertiesComponent givenPropertiesComponent() {
        PropertiesComponent pc = new PropertiesComponent("Type");
        pc.setBody(50, 50, 50, 50);
        pc.setCenter(100, 100);
        return pc;
    }

    @Test
    public void testPropertiesComponentClone() {
        PropertiesComponent propertiesComponent = new PropertiesComponent("Type");
        PropertiesComponent clone = propertiesComponent.clone();

        assertSame(propertiesComponent.getType(), clone.getType());
        assertNotSameButEquals(propertiesComponent.getBody(), clone.getBody());
        assertNotSameButEquals(propertiesComponent.getCenter(), clone.getCenter());
        assertNotSameButEquals(propertiesComponent.getPosition(), clone.getPosition());
    }

    @Test
    public void testFrameStateMachineComponentGetCurrentFrames() {
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();
        MockEffectFrame currentFrame = new MockEffectFrame(0, "A");
        fsmc.addFrame(currentFrame);
        fsmc.addFrame(new MockEffectFrame(1, "B"));
        fsmc.addFrame(new MockEffectFrame(2, "C"));
        fsmc.setCurrentFrame(currentFrame);

        assertSame(currentFrame, fsmc.getCurrentFrame());
    }

    @Test
    public void testFrameStateMachineComponentClone() {
        FrameStateMachineComponent fsmComponent = new FrameStateMachineComponent();
        MockEffectFrame mockEffectFrame = new MockEffectFrame(0, "Mock");
        fsmComponent.addFrame(mockEffectFrame);
        fsmComponent.setCurrentFrame(mockEffectFrame);

        FrameStateMachineComponent clone = fsmComponent.clone();

        assertNotSameButEquals(fsmComponent.getFiniteStateMachine(), clone.getFiniteStateMachine());
        assertSame(fsmComponent.getCurrentFrame(), clone.getCurrentFrame());
        assertDeepNotSameButEquals(fsmComponent.renderedFrameCollection, clone.renderedFrameCollection);
        assertDeepNotSameButEquals(fsmComponent.effectFrameMap, clone.effectFrameMap);
        assertSame(fsmComponent.world, clone.world);
        assertSame(fsmComponent.sprite, clone.sprite);
    }

    @Test
    public void testFrameStateMachineComponentEqualsAndHashcode() {
        FrameStateMachineComponent fscm1 = givenFrameStateMachineComponent();
        FrameStateMachineComponent fscm2 = givenFrameStateMachineComponent();
        assertEquals(fscm1, fscm2);
        assertEquals(fscm1.hashCode(), fscm2.hashCode());

        // after fscm2's been changed, they should no longer be equal
        fscm2.addFrame(new MockEffectFrame(0, "mock"));
        assertNotEquals(fscm1, fscm2);
        assertNotEquals(fscm1.hashCode(), fscm2.hashCode());
    }

    private FrameStateMachineComponent givenFrameStateMachineComponent() {
        return StubFactory.FrameStateMachineComponents.createFrameStateMachineComponentStub();
    }

}
