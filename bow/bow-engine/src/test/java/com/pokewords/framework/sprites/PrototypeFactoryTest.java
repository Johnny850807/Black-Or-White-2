package com.pokewords.framework.sprites;

import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.components.TextureFrame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrototypeFactoryTest {
    private PrototypeFactory prototypeFactory = new ReleaseIocFactory().prototypeFactory();
    private Sprite sprite;

    @Before
    public void setup(){
        sprite = new Sprite();
        PropertiesComponent propertiesComponent = new PropertiesComponent();
        FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
        frameStateMachineComponent.addState(new TextureFrame());
        sprite.setPropertiesComponent(propertiesComponent);
    }


    @Test
    public void testCloneExistentPrototype(){

    }

    @Test
    public void testCloneNonexistentPrototypeShouldThrowException(){

    }

    @Test
    public void testAddAndRemovePrototype(){

    }
}