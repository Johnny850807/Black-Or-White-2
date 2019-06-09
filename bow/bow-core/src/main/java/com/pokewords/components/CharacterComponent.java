package com.pokewords.components;

import com.pokewords.components.actions.ActionComponent;
import com.pokewords.constants.InjuryTypes;
import com.pokewords.framework.sprites.components.CloneableComponent;

import java.util.HashSet;

/**
 * @author johnny850807 (waterball)
 */
public class CharacterComponent extends CloneableComponent {
    private HashSet<Class<? extends ActionComponent>> ownedActionTypes = new HashSet<>();

    public void injure(InjuryTypes injuryType, int damage) {
        getOwnerSprite().getComponent(InjuryComponent.class).injure(injuryType, damage);
    }

    public void action(Class<? extends ActionComponent> actionType) {
        if (!ownedActionTypes.contains(actionType))
            throw new IllegalArgumentException("The action of the type " + actionType.getSimpleName() + " is not owned by the sprite " + getOwnerSprite().getType() + ".");

        getOwnerSprite().getComponent(actionType).action();
    }

    public void addActionType(Class<? extends ActionComponent> actionType) {
        ownedActionTypes.add(actionType);
    }

    public void removeActionType(Class<? extends ActionComponent> actionType) {
        ownedActionTypes.remove(actionType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CharacterComponent clone() {
        CharacterComponent clone = (CharacterComponent) super.clone();
        clone.ownedActionTypes = (HashSet<Class<? extends ActionComponent>>) ownedActionTypes.clone();
        return clone;
    }
}
