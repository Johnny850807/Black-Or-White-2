package Pacman;

import com.pokewords.framework.sprites.components.MouseListenerComponent;
import com.pokewords.framework.sprites.components.StringComponent;

import java.awt.*;

public class TextMouseListenerComponent extends MouseListenerComponent {

    @Override
    public void onMouseEnter(Point mousePositionInWorld, Point mousePositionInArea) {
        getOwnerSprite().getComponent(StringComponent.class).setColor(new Color(255, 255, 255));
    }

    @Override
    public void onMouseExit(Point mousePositionInWorld) {
        getOwnerSprite().getComponent(StringComponent.class).setColor(new Color(10, 10, 10));
    }
}
