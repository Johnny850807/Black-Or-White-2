package com.pokewords.appstates;

import com.pokewords.constants.AsmEvents;
import com.pokewords.constants.SpriteTypes;
import com.pokewords.constants.Theme;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

public class MenuAppState extends AppState {
    private Sprite playButton;
    private Sprite multiplayerButton;

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        configGameWindow();
        spawnUiSprites();
        bindMouseClickEvents();
    }

    private void configGameWindow() {
        getGameWindowsConfigurator().gameSize(800, 600)
                                .gamePanelBackground(Theme.mainColor);
    }

    private void spawnUiSprites() {
        getAppStateWorld().spawn(playButton = createSprite(SpriteTypes.MENU_PLAY_BUTTON));
        getAppStateWorld().spawn(multiplayerButton = createSprite(SpriteTypes.MENU_MULTIPLAYER_BUTTON));
    }


    private void bindMouseClickEvents() {
        bindMouseClickedAction(point -> {
            if (playButton.getArea().contains(point))
                getAppStateMachine().trigger(AsmEvents.GAME_START);
            else if (multiplayerButton.getArea().contains(point))
                getAppStateMachine().trigger(AsmEvents.TO_MULTIPLAYER);
        });
    }

    @Override
    protected void onAppStateEntering() { }

    @Override
    protected void onAppStateExiting() { }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(double timePerFrame) { }
}
