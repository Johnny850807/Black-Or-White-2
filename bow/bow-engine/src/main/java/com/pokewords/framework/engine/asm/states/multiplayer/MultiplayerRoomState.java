package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.asm.Bundle;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;
import java.util.ArrayList;

public class MultiplayerRoomState extends AppState {
    public final static String KEY_PLAYER_IP = "playerIp";
    public final static String KEY_PLAYER_NAME = "playerName";
    private ArrayList<Sprite> playerCards = new ArrayList<>(8);
    private Theme theme;
    private Player host;

    public MultiplayerRoomState() {
        this(new Theme(Color.decode("#191F26"), Color.white));
    }

    public MultiplayerRoomState(Theme theme) {
        this.theme = theme;
    }

    @Override
    public void onReceiveMessageBundle(Bundle bundle) {
        String ip = bundle.getStringOptional(KEY_PLAYER_IP)
                .orElseThrow(() -> new IllegalArgumentException("The bundle toward MultiplayerRoomState should be set key KEY_PLAYER_IP."));
        String name = bundle.getStringOptional(KEY_PLAYER_NAME)
                .orElseThrow(() -> new IllegalArgumentException("The bundle toward MultiplayerRoomState should be set key KEY_PLAYER_NAME."));

        this.host = new Player(ip, name, true);
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) { }


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
        playerCard.addComponent(new PlayerComponent(new PlayerCardFrame(0, 1, player)));
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
