package com.pokewords.components;

import com.pokewords.framework.sprites.components.CloneableComponent;

public class TeamComponent extends CloneableComponent {
    private int team;

    public TeamComponent(int team) {
        this.team = team;
    }

    public int getTeam() {
        return team;
    }
}
