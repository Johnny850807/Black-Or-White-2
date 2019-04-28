package com.pokewords.framework.engine.utils;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;

import java.awt.*;

import static com.pokewords.framework.engine.utils.StubUtility.FSCM.createFrameStateMachineComponentStub;

/**
 * This utility contains all the util methods for creating Stubs that can easily be tested.
 * @author johnny850807
 */
public class StubUtility {

    public static class Sprites {
        public final static Rectangle STUB_BODY = new Rectangle(50, 50, 100, 100);
        public final static String STUB_TYPE = "Stub";
        public final static String COLLIDABLE_COMP_NAME = "collidable component name";
        public final static CollidableComponent COLLIDABLE_COMPONENT = CollidableComponent.getInstance();

        /**
         * @return Sprite spec:
         * - PropertiesComponent
         *      - x: 50
         *      - y: 50
         *      - w: 100
         *      - h: 100
         *      - type: "Stub"
         *      - state: "Hello World"
         *
         * - FrameStateMachineComponent
         *      @see FSCM#createFrameStateMachineComponentStub()
         *
         * - CollidableComponent
         */
        public static Sprite createSpriteStub(){
            PropertiesComponent propertiesComponent = new PropertiesComponent();
            FrameStateMachineComponent frameStateMachineComponent = createFrameStateMachineComponentStub();
            Sprite spriteStub = new Sprite(frameStateMachineComponent, propertiesComponent);
            spriteStub.setBody(STUB_BODY);
            spriteStub.setType(STUB_TYPE);
            spriteStub.putComponent(COLLIDABLE_COMP_NAME, COLLIDABLE_COMPONENT);
            return spriteStub;
        }
    }

    public static class FSCM {
        public static final MockFrame FRAME_A = new MockFrame("A");
        public static final MockFrame FRAME_B = new MockFrame("B");
        public static final MockFrame FRAME_C = new MockFrame("C");

        /**
         * @return see visualization at "/specs/FSM-Stub.jpg"
         */
        public static FrameStateMachineComponent createFrameStateMachineComponentStub(){
            FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
            frameStateMachineComponent.addFrame(FRAME_A);
            frameStateMachineComponent.addFrame(FRAME_B);
            frameStateMachineComponent.addFrame(FRAME_C);
            frameStateMachineComponent.addTransition(FRAME_A, "1", FRAME_B);
            frameStateMachineComponent.addTransition(FRAME_B, "2", FRAME_C);
            frameStateMachineComponent.addTransition(FRAME_C, "3", FRAME_A);
            frameStateMachineComponent.setCurrentFrame(FRAME_A);
            return frameStateMachineComponent;
        }
    }


}
