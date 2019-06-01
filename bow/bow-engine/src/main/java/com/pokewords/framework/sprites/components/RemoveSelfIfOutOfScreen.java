package com.pokewords.framework.sprites.components;

import com.pokewords.framework.views.windows.GameWindowDefinition;

import java.awt.*;

/**
 * Any Sprite equipped with this component will be removed if it's out of screen
 * @author johnny850807 (waterball)
 */
public class RemoveSelfIfOutOfScreen extends CloneableComponent {

    @Override
    public void onUpdate(double timePerFrame) {
        super.onUpdate(timePerFrame);

        GameWindowDefinition windowDefinition = getGameEngineFacade().getGameWindowDefinition();
        Rectangle area = new Rectangle(0, 0, windowDefinition.size.width, windowDefinition.size.height);
        if (!area.contains(getOwnerSprite().getBody()) && !area.intersects(getOwnerSprite().getBody()))
            getAttachedWorld().removeSprite(getOwnerSprite());
    }
}
