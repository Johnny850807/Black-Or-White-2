package com.pokewords.framework.engine.utils;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;

import java.awt.*;
import java.awt.geom.Point2D;

import static com.pokewords.framework.engine.utils.StubUtility.FrameStateMachineComponents.createFrameStateMachineComponentStub;

public class StubUtility {

    public static class Sprites {
        public final static Point2D STUB_POINT = new Point(10, 10);
        public final static String STUB_STATE = "Hello World";
        public final static String STUB_TYPE = "Stub";

        public static Sprite createSpriteStub(){
            PropertiesComponent propertiesComponent = new PropertiesComponent();
            FrameStateMachineComponent frameStateMachineComponent = createFrameStateMachineComponentStub();
            Sprite spriteStub = new Sprite(frameStateMachineComponent, propertiesComponent);
            spriteStub.setPosition(STUB_POINT);
            spriteStub.setState(STUB_STATE);
            spriteStub.setType(STUB_TYPE);


            return spriteStub;
        }
    }

    public static class FrameStateMachineComponents {
        public static final MockFrame FRAME_A = new MockFrame("A");
        public static final MockFrame FRAME_B = new MockFrame("B");
        public static final MockFrame FRAME_C = new MockFrame("C");

        public static FrameStateMachineComponent createFrameStateMachineComponentStub(){
            FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
            frameStateMachineComponent.addState(FRAME_A);
            frameStateMachineComponent.addState(FRAME_B);
            frameStateMachineComponent.addState(FRAME_C);
            frameStateMachineComponent.addTransition(FRAME_A, "1", FRAME_B);
            frameStateMachineComponent.addTransition(FRAME_B, "2", FRAME_C;
            frameStateMachineComponent.addTransition(FRAME_C, "3", FRAME_A);
            return frameStateMachineComponent;
        }
    }


}
