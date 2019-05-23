package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.sprites.components.FrameComponent;

public class PlayerComponent extends FrameComponent<PlayerCardFrame> {
    private Player player;

    public PlayerComponent(PlayerCardFrame frame) {
        super(frame);
        this.player = frame.getPlayer();
    }

    public Player getPlayer() {
        return player;
    }

    public void setReady(boolean playerReady) {
        player.ready = playerReady;
    }
}
