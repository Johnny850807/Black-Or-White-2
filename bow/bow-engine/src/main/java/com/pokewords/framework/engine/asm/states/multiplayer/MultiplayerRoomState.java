package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.commons.bundles.Bundle;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;
import java.util.ArrayList;

public class MultiplayerRoomState extends AppState {
    public final static String KEY_PLAYER_IP_STRING = "playerIp";
    public final static String KEY_PLAYER_NAME_STRING = "playerName";
    public final static String KEY_THEME = "theme";
    private ArrayList<Sprite> playerCards = new ArrayList<>(8);
    private Theme theme = new Theme(Color.decode("#191F26"), Color.white);
    private Player host;



    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) { }


    @Override
    public void onReceiveMessageBundle(Bundle bundle) {
        String ip = bundle.getStringOptional(KEY_PLAYER_IP_STRING)
                .orElseThrow(() -> new IllegalArgumentException("The bundle toward MultiplayerRoomState should be set key KEY_PLAYER_IP_STRING."));
        String name = bundle.getStringOptional(KEY_PLAYER_NAME_STRING)
                .orElseThrow(() -> new IllegalArgumentException("The bundle toward MultiplayerRoomState should be set key KEY_PLAYER_NAME_STRING."));

        theme = (Theme) bundle.getOptional(KEY_THEME).orElse(theme);


        this.host = new Player(ip, name, true);
    }

    @Override
    protected void onAppStateEntering() {
        configWindows();
        getSpriteInitializer().declare(Types.CARD)
                .with(MouseListenerComponent.ofListener(new CardMouseListener()))
                .area(30, 30, 150, 80).commit();
        host.ready = true;
        spawnPlayerCard(host);
    }


    private void configWindows() {
        getGameWindowsConfigurator().gamePanelBackground(theme.backgroundColor)
                .gameSize(800, 600);
    }

    private void spawnPlayerCard(Player player) {
        Sprite playerCard = createSprite(Types.CARD);
        playerCards.add(playerCard);
        playerCard.addComponent(new PlayerComponent(new PlayerCardFrame(1, player)));
        getAppStateWorld().spawn(playerCard);
    }


    public Sprite getHostCardSprite() {
        return playerCards.get(0);
    }

    @Override
    protected void onAppStateExiting() {
        getAppStateWorld().clearSprites();
    }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(double timePerFrame) { }

    public static class Theme {
        public Color backgroundColor;
        public Color cardColor;

        public Theme(Color backgroundColor, Color cardColor) {
            this.backgroundColor = backgroundColor;
            this.cardColor = cardColor;
        }
    }
}
