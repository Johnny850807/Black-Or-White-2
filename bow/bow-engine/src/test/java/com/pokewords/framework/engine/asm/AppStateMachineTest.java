package com.pokewords.framework.engine.asm;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.windows.GameFrameWindowsConfigurator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppStateMachineTest extends AbstractTest {
    private AppStateMachine appStateMachine;

    @Before
    public void setup() {
        appStateMachine = new AppStateMachine(release.inputManager(),
                new SpriteInitializer(release), new GameFrameWindowsConfigurator()
        ))
    }

    @Test
    public void testLifecycleEventsDelegatingProperly() {

    }

}