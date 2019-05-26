package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.MockGameEngine;
import com.pokewords.framework.engine.asm.states.EmptyAppState;
import com.pokewords.framework.sprites.MockSprite;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.windows.MockGameWindowsConfigurator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppStateWorldTest extends AbstractTest {
    private AppStateWorld appStateWorld = new AppStateWorld(new EmptyAppState(),
            new GameEngineFacade(release, new MockGameEngine(),
                    new MockGameWindowsConfigurator()));

    @Test
    public void testAppStateWorldLifecycleHasBeenDelegatedToSprite() {
        final int updateLoopCount = 100;

        List<MockSprite> sprites = creatSprites(20);
        for (Sprite sprite : sprites)
            appStateWorld.spawn(sprite);

        appStateWorld.onUpdate(15);  // only after the update, the sprites are actually spawned

        appStateWorld.onAppStateCreate();
        appStateWorld.onAppStateEnter();

        for (int i = 0; i < updateLoopCount; i++) {
            appStateWorld.onUpdate(15);
        }

        appStateWorld.onAppStateExit();
        appStateWorld.onAppStateDestroy();

        for (MockSprite sprite : sprites) {
            assertEquals(1, sprite.onAppStateStartCount);
            assertEquals(1, sprite.onAppStateEnterCount);
            assertEquals(updateLoopCount+1, sprite.onUpdateCount);
            assertEquals(1, sprite.onAppStateExitCount);
            assertEquals(1, sprite.onAppStateDestroyCount);
        }

    }


    private List<MockSprite> creatSprites(int count) {
        List<MockSprite> sprites = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sprites.add(new MockSprite("mock"));
        }
        return sprites;
    }

}
