package com.pokewords.appstates;

import com.pokewords.constants.Events;
import com.pokewords.constants.SoundTypes;
import com.pokewords.constants.SpriteTypes;
import com.pokewords.constants.Theme;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.commons.bundles.Bundle;
import com.pokewords.framework.engine.asm.states.multiplayer.MultiplayerRoomState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

public class MenuAppState extends AppState {
    private Sprite menu;
    private int loop = 0;
    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        appStateWorld.spawn(menu = createSprite(SpriteTypes.MENU));

        configGameWindow();

    }

    private void configGameWindow() {
        getGameWindowsConfigurator().gameSize(menu.getBodySize())
                                .gamePanelBackground(Theme.mainColor);
    }

    @Override
    protected void onAppStateEntering() {
    }

    @Override
    protected void onAppStateExiting() {
        getSoundPlayer().stop(SoundTypes.OPENING);
    }

    @Override
    protected void onAppStateDestroying() {

    }

    @Override
    protected void onAppStateUpdating(double timePerFrame) {
        if (loop ++ == 200)
        {
            Bundle message = new Bundle();
            message.put(MultiplayerRoomState.KEY_PLAYER_NAME_STRING, "水球");
            message.put(MultiplayerRoomState.KEY_PLAYER_IP_STRING, "125.37.1.65");
            getAppStateMachine().trigger(Events.TO_MULTIPLAYER, message);
        }
    }
}
