package com.pokewords.framework.sprites.components;

import com.pokewords.framework.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ComponentsTest extends AbstractTest {

    @Test
    public void testPropertiesComponentClone() {
        PropertiesComponent propertiesComponent = new PropertiesComponent();
        PropertiesComponent clone = propertiesComponent.clone();
        assertNotSame(propertiesComponent.getBody(), clone.getBody());
        assertSame(propertiesComponent.getState(), clone.getState());
        assertSame(propertiesComponent.getType(), clone.getType());

        // listeners should be re-initialized
        assertNotSame(propertiesComponent.getPositionListeners(), clone.getPositionListeners());
        assertNotSame(propertiesComponent.getStateListeners(), clone.getStateListeners());
    }

    @Test
    public void testFrameStateMachineClone() {
        FrameStateMachineComponent fsmComponent = new FrameStateMachineComponent();
        FrameStateMachineComponent clone = fsmComponent.clone();
        assertSame(fsmComponent.getFiniteStateMachine(), clone.getFiniteStateMachine());
        assertSame(fsmComponent.getAppStateWorld(), clone.getAppStateWorld());
    }

    @Test
    public void testCollidableComponentClone() {
        CollidableComponent collidableComponent = new CollidableComponent();
        assertNotSame(collidableComponent.clone(), collidableComponent);
    }
}
