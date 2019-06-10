package com.pokewords.components;

import com.pokewords.constants.InjuryTypes;
import com.pokewords.framework.sprites.components.CloneableComponent;

/**
 * @author johnny850807 (waterball)
 */
public class DefenseComponent extends CloneableComponent {
    private int defense;

    public DefenseComponent() {
        this(0);
    }

    public DefenseComponent(int defense) {
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * @return the damage point reduced by the defense
     */
    public int defenseFromInjury(InjuryTypes type, int damage) {
        return damage - defense;  // TODO
    }
}
