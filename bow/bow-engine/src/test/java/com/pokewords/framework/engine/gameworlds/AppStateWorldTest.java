package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.sprites.MockSprite;
import com.pokewords.framework.sprites.Sprite;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppStateWorldTest {
    private AppStateWorld appStateWorld = new AppStateWorld();

    @Test
    public void testAppStateWorldLifecycleHasBeenDelegatedToSprite() {
        final int updateLoopCount = 100;

        List<MockSprite> sprites = creatSprites(20);
        for (Sprite sprite : sprites)
            appStateWorld.spawn(sprite);

        appStateWorld.onAppStateStart(appStateWorld);
        appStateWorld.onAppStateEnter();

        for (int i = 0; i < updateLoopCount; i++) {
            appStateWorld.onUpdate(15);
        }

        appStateWorld.onAppStateExit();
        appStateWorld.onAppStateDestroy();

        for (MockSprite sprite : sprites) {
            assertEquals(1, sprite.onAppStateStartCount);
            assertEquals(1, sprite.onAppStateEnterCount);
            assertEquals(updateLoopCount, sprite.onUpdateCount);
            assertEquals(1, sprite.onAppStateExitCount);
            assertEquals(1, sprite.onAppStateDestroyCount);
        }

    }


    private List<MockSprite> creatSprites(int count) {
        List<MockSprite> sprites = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sprites.add(new MockSprite());
        }
        return sprites;
    }

}
