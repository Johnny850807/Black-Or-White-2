package com.pokewords.framework.sprites.components;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubUtility;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentsTest extends AbstractTest {

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
    public void testFrameStateMachineClone() {
        FrameStateMachineComponent fsmComponent = new FrameStateMachineComponent();
        FrameStateMachineComponent clone = fsmComponent.clone();
        assertSame(fsmComponent.getFiniteStateMachine(), clone.getFiniteStateMachine());
        assertSame(fsmComponent.world, clone.world);
        assertSame(fsmComponent.sprite, clone.sprite);
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
    public void testFrameStateMachineComponent() {
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
        return StubUtility.FSCM.createFrameStateMachineComponentStub();
    }

    @Test
    public void testCollidableComponentEqualsAndHashcode() {
        assertEquals(CollidableComponent.getInstance(), CollidableComponent.getInstance());
        assertEquals(CollidableComponent.getInstance().hashCode(),
                CollidableComponent.getInstance().hashCode());
    }
}
