package Pacman;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;

@SuppressWarnings("Duplicates")
public class PlayerMouseListenerComponent extends MouseListenerComponent {
    @Override
    public void onMousePressed(Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("press");
    }

    @Override
    public void onMouseReleased(Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("released");
    }

    @Override
    public void onMouseClicked(Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("clicked");
    }

    @Override
    public void onMouseEnter(Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseEnter(mousePositionInWorld, mousePositionInArea);
        System.out.println("enter");
        getOwnerSprite().getComponent(ImageComponent.class).setImage("images/cry.png");
    }

    @Override
    public void onMouseExit(Point mousePositionInWorld) {
        super.onMouseExit(mousePositionInWorld);
        getOwnerSprite().getComponent(ImageComponent.class).setImage("images/smile.png");
    }
}
