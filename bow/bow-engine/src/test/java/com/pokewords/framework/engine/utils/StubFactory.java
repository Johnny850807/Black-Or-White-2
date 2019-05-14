package com.pokewords.framework.engine.utils;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.components.mocks.*;


import java.awt.*;

import static com.pokewords.framework.engine.utils.StubFactory.FrameStateMachineComponents.createFrameStateMachineComponentStub;

/**
 * This utility contains all the util methods for creating Stubs that can easily be tested.
 * @author johnny850807
 */
public interface StubFactory {
    interface Sprites {
        interface SimpleSprite {
            Rectangle BODY = new Rectangle(50, 50, 100, 100);
            String TYPE = "Stub";
            CollidableComponent COLLIDABLE_COMPONENT = new CollidableComponent();

            /**
             * @return Sprite spec:
             * - PropertiesComponent
             *      - x: 50
             *      - y: 50
             *      - w: 100
             *      - h: 100
             *      - type: "Stub"
             *
             * - FrameStateMachineComponent
             *      @see FrameStateMachineComponents#createFrameStateMachineComponentStub()
             *
             * - CollidableComponent
             * - MockClickableComponent
             */
            static Sprite createSimpleSprite(){
                PropertiesComponent propertiesComponent = new PropertiesComponent();
                FrameStateMachineComponent frameStateMachineComponent = createFrameStateMachineComponentStub();
                Sprite spriteStub = new Sprite(propertiesComponent);
                spriteStub.addComponent(frameStateMachineComponent);
                spriteStub.setBody(BODY);
                spriteStub.setType(TYPE);
                spriteStub.addComponent(COLLIDABLE_COMPONENT);
                return spriteStub;
            }
        }

        interface SpriteWithOnlyMockComponent {
            String TYPE = "Type";

            /**
             * @return Spec:
             * MockComponent1,
             * MockComponent2,
             * MockComponent3
             * MockPropertiesComponent :
             *      - type: 'Type'
             * MockFrameStateMachineComponent : empty
             */
            static Sprite createSpriteWithOnlyMockComponents() {
                MockComponent1 mockComponent1 = new MockComponent1();
                MockComponent2 mockComponent2 = new MockComponent2();
                MockComponent3 mockComponent3 = new MockComponent3();
                MockPropertiesComponent pc = new MockPropertiesComponent(TYPE);
                MockFrameStateMachineComponent fsmc = new MockFrameStateMachineComponent();

                Sprite sprite = new Sprite(pc);
                sprite.addComponent(fsmc);
                sprite.addComponent(mockComponent1);
                sprite.addComponent(mockComponent2);
                sprite.addComponent(mockComponent3);
                return sprite;
            }
        }
    }

    interface FrameStateMachineComponents {
        MockFrame FRAME_A = new MockFrame("A");
        MockFrame FRAME_B = new MockFrame("B");
        MockFrame FRAME_C = new MockFrame("C");

        /**
         * @return see visualization at "/specs/FSM-Stub.jpg"
         */
        static FrameStateMachineComponent createFrameStateMachineComponentStub(){
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
