package com.pokewords.framework.engine.utils;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.MockFrameFactory;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.awt.*;
import java.awt.geom.Point2D;

public class StubUtility {

    public static class Sprites {
        public final static Point2D STUB_POINT = new Point(10, 10);
        public final static String STUB_STATE = "Hello World";
        public final static String STUB_TYPE = "Stub";

        public static Sprite createSpriteStub(){
            PropertiesComponent propertiesComponent = new PropertiesComponent();
            FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
            FrameFactory frameFactory = new MockFrameFactory();

            Sprite spriteStub = new Sprite(frameStateMachineComponent, propertiesComponent);
            spriteStub.setPosition(STUB_POINT);
            spriteStub.setState(STUB_STATE);
            spriteStub.setType(STUB_TYPE);


            return spriteStub;
        }
    }


}
