package com.pokewords.framework.networking;

import com.pokewords.framework.engine.asm.states.multiplayer.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PlayerClient implements Client {
    private Player player;
    private Client client;

    public PlayerClient(Player player, Client client) {
        this.player = player;
        this.client = client;

        assert player.ip.equals(client.getIp());
    }

    public String getName() {
        return player.name;
    }

    public boolean isReady() {
        return player.ready;
    }

    @Override
    public String getIp() {
        return client.getIp();
    }

    @Override
    public int getId() {
        return client.getId();
    }

    @Override
    public void broadcast(byte[] rawMessage) throws IOException {
        client.broadcast(rawMessage);
    }

    @Override
    public InputStream getInputStream() {
        return client.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return client.getOutputStream();
    }

    public Player getPlayer() {
        return player;
    }

    public Client getClient() {
        return client;
    }
}
