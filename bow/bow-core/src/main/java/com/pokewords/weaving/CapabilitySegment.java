package com.pokewords.weaving;

import com.pokewords.framework.sprites.parsing.Segment;

/**
 * @author johnny850807 (waterball)
 */
public class CapabilitySegment {
    private int hp;
    private int defense;

    public CapabilitySegment(Segment segment) {
        this.hp = segment.getInt("hp");
        this.defense = segment.getInt("defense");
    }

    public int getHp() {
        return hp;
    }

    public int getDefense() {
        return defense;
    }
}

