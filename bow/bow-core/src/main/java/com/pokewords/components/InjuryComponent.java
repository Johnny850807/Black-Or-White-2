package com.pokewords.components;

import com.pokewords.constants.InjuryTypes;
import com.pokewords.framework.sprites.components.CloneableComponent;
import com.pokewords.framework.sprites.components.HpComponent;

/**
 * @author johnny850807 (waterball)
 */
public class InjuryComponent extends CloneableComponent {

    public void injure(InjuryTypes type, int damage) {
        getOwnerSprite().getComponent(HpComponent.class).lostHp(countDamage(type, damage));
        makeInjuryEffect(type, damage);
    }

    private int countDamage(InjuryTypes type, int damage) {
        if (getOwnerSprite().hasComponent(DefenseComponent.class))
            return getOwnerSprite().getComponent(DefenseComponent.class).defenseFromInjury(type, damage);
        //TODO
        return damage;
    }

    private void makeInjuryEffect(InjuryTypes type, int damage) {
        //TODO
    }
}
