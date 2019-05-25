package homework;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.KeyListenerComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class PlayerMouseListener extends MouseListenerComponent.Listener {
    @Override
    public void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("press");
    }

    @Override
    public void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("released");
    }

    @Override
    public void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("clicked");
    }

    @Override
    public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        System.out.println("enter");
        sprite.getComponent(ImageComponent.class).setImage("images/cry.png");
    }

    @Override
    public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
        sprite.getComponent(ImageComponent.class).setImage("images/smile.png");
    }
}
