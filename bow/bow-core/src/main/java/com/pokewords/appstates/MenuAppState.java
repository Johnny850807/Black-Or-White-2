package com.pokewords.appstates;

import com.pokewords.constants.SpriteTypes;
import com.pokewords.constants.Theme;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

public class MenuAppState extends AppState {
    private Sprite menu;
    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        appStateWorld.spawn(menu = createSprite(SpriteTypes.MENU));

        configGameWindow();

    }

    private void configGameWindow() {
        getGameWindowsConfigurator().gameSize(menu.getBodySize())
                                .gamePanelBackground(Theme.mainColor)
                                .atCenter();
    }

    @Override
    protected void onAppStateEntering() {
    }

    @Override
    protected void onAppStateExiting() {
        //getSoundPlayer().stop(SoundTypes.OPENING);
    }

    @Override
    protected void onAppStateDestroying() {

    }

    @Override
    protected void onAppStateUpdating(double timePerFrame) {

    }
}
