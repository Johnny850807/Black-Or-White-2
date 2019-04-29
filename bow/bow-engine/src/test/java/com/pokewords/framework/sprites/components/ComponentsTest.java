package com.pokewords.framework.sprites.components;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubFactory;
import com.pokewords.framework.sprites.components.mocks.MockFrame;
import com.pokewords.framework.sprites.components.mocks.MockPositionListener;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ComponentsTest extends AbstractTest {

    @Test
    public void testPropertiesComponentPositionListener() {
        PropertiesComponent pc = new PropertiesComponent("Type");
        MockPositionListener listener = new MockPositionListener();
        pc.addPositionListener(listener);

        pc.setPosition(50, 100);
        assertMockPositionListenerTriggered(listener, 1, 50, 100);

        pc.setBody(100, 200, 500, 500);
        assertMockPositionListenerTriggered(listener, 2, 100, 200);

        pc.setPosition(new Point(1, 2));
        assertMockPositionListenerTriggered(listener, 3, 1, 2);

        pc.setBody(new Rectangle(400, 500, 500, 500));
        assertMockPositionListenerTriggered(listener, 4, 400, 500);
    }

    private void assertMockPositionListenerTriggered(MockPositionListener listener, int triggeredCount, int x, int y) {
        assertEquals(triggeredCount, listener.getTriggerCount());
        assertEquals(x, listener.getX());
        assertEquals(y, listener.getY());
    }


    @Test
    public void testPropertiesComponentAccessors() {
        PropertiesComponent pc = new PropertiesComponent("Type");
        pc.setBody(50, 50, 50, 50);
        pc.setCenter(200, 200);

        assertEquals("Type", pc.getType());
        assertEquals(50, pc.getX());
        assertEquals(50, pc.getY());
        assertEquals(50, pc.getW());
        assertEquals(50, pc.getH());
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
        assertNotSame(propertiesComponent.getBody(), clone.getBody());
        assertSame(propertiesComponent.getType(), clone.getType());

        // listeners should be re-initialized
        assertNotSame(propertiesComponent.getPositionListeners(), clone.getPositionListeners());
    }

    @Test
    public void testFrameStateMachineComponentGetCurrentFrames() {
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();
        MockFrame currentFrame = new MockFrame("A");
        fsmc.addFrame(currentFrame);
        fsmc.addFrame(new MockFrame("B"));
        fsmc.addFrame(new MockFrame("C"));
        fsmc.setCurrentFrame(currentFrame);

        assertSame(currentFrame, fsmc.getCurrentFrame());
    }

    @Test
    public void testFrameStateMachineComponentClone() {
        FrameStateMachineComponent fsmComponent = new FrameStateMachineComponent();
        FrameStateMachineComponent clone = fsmComponent.clone();
        assertSame(fsmComponent.getFiniteStateMachine(), clone.getFiniteStateMachine());
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
        fscm2.addFrame(new MockFrame("mock"));
        assertNotEquals(fscm1, fscm2);
        assertNotEquals(fscm1.hashCode(), fscm2.hashCode());
    }

    private FrameStateMachineComponent givenFrameStateMachineComponent() {
        return StubFactory.FSCM.createFrameStateMachineComponentStub();
    }

    @Test
    public void testCollidableComponentEqualsAndHashcode() {
        assertEquals(CollidableComponent.getInstance(), CollidableComponent.getInstance());
        assertEquals(CollidableComponent.getInstance().hashCode(),
                CollidableComponent.getInstance().hashCode());
    }
}
