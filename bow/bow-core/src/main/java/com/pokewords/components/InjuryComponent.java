package com.pokewords.components;

import com.pokewords.constants.InjuryTypes;
import com.pokewords.framework.sprites.components.CloneableComponent;
import com.pokewords.framework.sprites.components.HpComponent;

public class InjuryComponent extends CloneableComponent {

    public void injure(InjuryTypes type, int damage) {
        getOwnerSprite().getComponent(HpComponent.class).lostHp(countDamage(type, damage));
        makeInjuryEffect(type, damage);
    }

    private int countDamage(InjuryTypes type, int damage) {
        //TODO
        return damage;
    }

    private void makeInjuryEffect(InjuryTypes type, int damage) {
        //TODO
    }
}
