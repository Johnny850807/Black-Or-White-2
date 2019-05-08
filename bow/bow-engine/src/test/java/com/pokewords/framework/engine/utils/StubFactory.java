package com.pokewords.framework.engine.utils;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollidableComponent;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.components.mocks.MockComponentImp;
import com.pokewords.framework.sprites.components.mocks.MockFrame;
import com.pokewords.framework.sprites.components.mocks.MockFrameStateMachineComponent;
import com.pokewords.framework.sprites.components.mocks.MockPropertiesComponent;


import java.awt.*;

import static com.pokewords.framework.engine.utils.StubFactory.FSCM.createFrameStateMachineComponentStub;

/**
 * This utility contains all the util methods for creating Stubs that can easily be tested.
 * @author johnny850807
 */
public interface StubFactory {
    interface Sprites {
        interface SimpleSprite {
            Rectangle STUB_BODY = new Rectangle(50, 50, 100, 100);
            String STUB_TYPE = "Stub";
            String COLLIDABLE_COMP_NAME = "collidable component name";
            CollidableComponent COLLIDABLE_COMPONENT = CollidableComponent.getInstance();

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
             *      @see FSCM#createFrameStateMachineComponentStub()
             *
             * - CollidableComponent
             */
            static Sprite createSimpleSprite(){
                PropertiesComponent propertiesComponent = new PropertiesComponent();
                FrameStateMachineComponent frameStateMachineComponent = createFrameStateMachineComponentStub();
                Sprite spriteStub = new Sprite(propertiesComponent);
                spriteStub.putComponent("FSM", frameStateMachineComponent);
                spriteStub.setBody(STUB_BODY);
                spriteStub.setType(STUB_TYPE);
                spriteStub.putComponent(COLLIDABLE_COMP_NAME, COLLIDABLE_COMPONENT);
                return spriteStub;
            }
        }

        interface SpriteWithOnlyMockComponent {
            String TYPE = "Type";
            String MOCK1 = "Mock1";
            String MOCK2 = "Mock2";
            String MOCK3 = "Mock3";

            /**
             * @return Spec:
             * 3 MockComponentImp :
             *      - their names are: 'Mock1', 'Mock2' and 'Mock3'
             * MockPropertiesComponent :
             *      - type: 'Type'
             * MockFrameStateMachineComponent : empty
             */
            static Sprite createSpriteWithOnlyMockComponents() {
                MockComponentImp mockComponent1 = new MockComponentImp();
                MockComponentImp mockComponent2 = new MockComponentImp();
                MockComponentImp mockComponent3 = new MockComponentImp();
                MockPropertiesComponent pc = new MockPropertiesComponent(TYPE);
                MockFrameStateMachineComponent mfsmc = new MockFrameStateMachineComponent();

                Sprite sprite = new Sprite(pc);
                sprite.putComponent("FSM", mfsmc);
                sprite.putComponent(MOCK1, mockComponent1);
                sprite.putComponent(MOCK2, mockComponent2);
                sprite.putComponent(MOCK3, mockComponent3);
                return sprite;
            }
        }
    }

    interface FSCM {
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
