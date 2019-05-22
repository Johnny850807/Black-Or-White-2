package com.pokewords.framework.engine.asm;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.Events;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.effects.NoTransitionEffect;
import com.pokewords.framework.views.sound.MockSoundPlayer;
import com.pokewords.framework.views.windows.GameFrameWindowsConfigurator;
import com.pokewords.framework.views.windows.MockGameWindowsConfigurator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author johnny850807 (waterball)
 */
public class AppStateMachineTest extends AbstractTest {
    private AppStateMachine appStateMachine;
    private int EVENT_NEXT = 0;

    @Before
    public void setup() {
        appStateMachine = new AppStateMachine(release, release.inputManager(),
                new SpriteInitializer(release), new MockGameWindowsConfigurator(), new MockSoundPlayer());
    }

    @Test
    public void testLifecycleEventsDelegatingProperly() {
        MockAppState A = appStateMachine.createState(MockAppState.class);
        MockAppState B = appStateMachine.createState(MockAppState.class);
        MockAppState C = appStateMachine.createState(MockAppState.class);

        appStateMachine.setGameInitialState(A, NoTransitionEffect.getInstance());
        appStateMachine.addTransition(A, EVENT_NEXT, B);
        appStateMachine.addTransition(B, EVENT_NEXT, C);
        appStateMachine.addTransition(C, EVENT_NEXT, A);

        assertEquals(1, A.getOnAppStateCreatingCount());
        assertEquals(1, B.getOnAppStateCreatingCount());
        assertEquals(1, C.getOnAppStateCreatingCount());

        assertSame(appStateMachine.getLoadingState(), appStateMachine.trigger(AppStateMachine.EVENT_LOADING));
        assertSame(appStateMachine.getGameInitialState(), appStateMachine.trigger(AppStateMachine.EVENT_GAME_STARTED));
        assertSame(A, appStateMachine.getGameInitialState());
        assertTrue(A.isRunning());

        assertSame(B,  appStateMachine.trigger(EVENT_NEXT));
        assertSame(B, appStateMachine.getCurrentState());
        assertFalse(A.isRunning());
        assertTrue(B.isRunning());

        assertSame(C,  appStateMachine.trigger(EVENT_NEXT));
        assertSame(C, appStateMachine.getCurrentState());
        assertFalse(B.isRunning());
        assertTrue(C.isRunning());

        assertSame(A,  appStateMachine.trigger(EVENT_NEXT));
        assertSame(A, appStateMachine.getCurrentState());
        assertFalse(C.isRunning());
        assertTrue(A.isRunning());

        for (int i = 0; i < 15; i++) {
            appStateMachine.onUpdate(15);
        }
        assertEquals(15, A.getOnAppStateUpdating());

    }

}