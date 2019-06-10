package com.pokewords.components;

import com.pokewords.components.actions.ActionComponent;
import com.pokewords.constants.InjuryTypes;
import com.pokewords.framework.sprites.components.CloneableComponent;

/**
 * @author johnny850807 (waterball)
 */
public class CharacterComponent extends CloneableComponent {

    public void injure(InjuryTypes injuryType, int damage) {
        getOwnerSprite().getComponent(InjuryComponent.class).injure(injuryType, damage);
    }

    public void action(Class<? extends ActionComponent> actionType) {
        getOwnerSprite().getComponent(actionType).action();
    }
}
