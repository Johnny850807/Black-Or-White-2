package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.commons.utils.NetUtility;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.commons.bundles.Bundle;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.networking.Client;
import com.pokewords.framework.networking.PlayerClient;
import com.pokewords.framework.networking.SessionServer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author johnny850807 (waterball)
 */
public abstract class MultiplayerRoomState extends AppState implements SessionServer.ClientListener {
    public final static String KEY_PLAYER_NAME_STRING = "playerName";
    public final static String KEY_THEME = "theme";
    public final static String KEY_SERVER_PORT = "serverPort";
    public final static int DEFAULT_SERVER_PORT = 8087;
    private int serverPort;
    private Map<Integer, PlayerClient> idPlayerMap = new HashMap<>();
    private ArrayList<Sprite> playerCards = new ArrayList<>(8);
    private Theme theme = new Theme(Color.decode("#191F26"), Color.white);
    private Player host;
    private SessionServer sessionServer;


    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) { }


    @Override
    public void onReceiveMessageBundle(Bundle bundle) {
        serverPort = bundle.getIntOptional(KEY_SERVER_PORT).orElse(DEFAULT_SERVER_PORT);

        String name = bundle.getStringOptional(KEY_PLAYER_NAME_STRING)
                .orElseThrow(() -> new IllegalArgumentException("The bundle toward MultiplayerRoomState should be set key KEY_PLAYER_NAME_STRING."));

        theme = (Theme) bundle.getOptional(KEY_THEME).orElse(theme);


        this.host = new Player(NetUtility.getIp(), name, true);
    }

    @Override
    protected void onAppStateEntering() {
        arrangeStateScene();
        //launchSessionServer();
    }

    private void arrangeStateScene() {
        configWindows();
        getSpriteInitializer().declare(Types.CARD)
                .with(new CardMouseListenerComponent())
                .area(30, 30, 150, 80).commit();
        host.ready = true;
        spawnPlayerCard(host);
    }

    private void spawnPlayerCard(Player player) {
        Sprite playerCard = createSprite(Types.CARD);
        playerCards.add(playerCard);
        playerCard.addComponent(new PlayerComponent(new PlayerCardFrame(1, player)));
        playerCard.setPosition(computeNextPlayerCardPosition());
        getAppStateWorld().spawn(playerCard);
    }

    private void configWindows() {
        getGameWindowsConfigurator().gamePanelBackground(theme.backgroundColor)
                .gameSize(800, 600);
    }

    private void launchSessionServer() {
        sessionServer = new SessionServer(serverPort);
        sessionServer.addClientListener(this);
        onServerInit(sessionServer);
        sessionServer.launchServer();
    }

    @Override
    public final void onClientConnected(Client client) {
        try {
         Player player = (Player) new ObjectInputStream(client.getInputStream()).readObject();
         PlayerClient playerClient = new PlayerClient(player, client);
         idPlayerMap.put(playerClient.getId(), playerClient);
         spawnPlayerCard(player);
         onClientConnected(playerClient);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onClientDisconnected(Client client) {
        onClientDisconnected(idPlayerMap.get(client.getId()));
    }

    protected abstract void onClientConnected(PlayerClient playerClient);
    protected abstract void onClientDisconnected(PlayerClient playerClient);
    protected abstract void onServerInit(SessionServer sessionServer);


    private Point computeNextPlayerCardPosition() {
        return new Point(30, 30); //TODO
    }

    public final Sprite getHostCardSprite() {
        return playerCards.get(0);
    }

    @Override
    protected void onAppStateExiting() {
        getAppStateWorld().clearSprites();
        sessionServer.removeClientListener(this);
        playerCards.clear();
        sessionServer = null;
        host = null;
        idPlayerMap.clear();
    }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(double timePerFrame) { }

    public static class Theme {
        public Color backgroundColor;
        public Color cardColor;

        public Theme(Color backgroundColor, Color cardColor) {
            this.backgroundColor = backgroundColor;;
            this.cardColor = cardColor;
        }
    }
}

