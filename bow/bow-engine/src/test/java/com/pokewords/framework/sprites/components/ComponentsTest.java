package com.pokewords.framework.sprites.components;

import com.pokewords.framework.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentsTest extends AbstractTest {

    @Test
    public void testPropertiesComponentClone(){
        PropertiesComponent propertiesComponent = new PropertiesComponent();
        PropertiesComponent clone = propertiesComponent.clone();
        assertNotSame(propertiesComponent.getPoint(), clone.getPoint());
        assertSame(propertiesComponent.getState(), clone.getState());
        assertSame(propertiesComponent.getType(), clone.getType());
        assertNotSame(propertiesComponent.getPositionListeners(), clone.getPositionListeners());
        assertNotSame(propertiesComponent.getStateListeners(), clone.getStateListeners());
    }

    @Test
    public void testFrameStateMachineClone(){
        FrameStateMachineComponent fsmComponent = new FrameStateMachineComponent();
        FrameStateMachineComponent clone = fsmComponent.clone();
        assertEquals(fsmComponent);
    }

    @Test
    public void testCollidableComponentClone(){
        CollidableComponent collidableComponent = new CollidableComponent();
        assertNotSame(collidableComponent.clone(), collidableComponent);
    }
}
