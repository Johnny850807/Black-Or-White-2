package com.pokewords.framework.engine.asm.states.multiplayer;

import java.io.Serializable;

public class Player implements Serializable {
    public String ip;
    public String name;
    public boolean ready = false;

    public Player(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public Player(String ip, String name, boolean ready) {
        this.ip = ip;
        this.name = name;
        this.ready = ready;
    }


}
