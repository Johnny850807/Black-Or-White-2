package homework;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class PlayerMouseListener extends MouseListenerComponent.Listener {
    @Override
    public void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMousePressed(sprite, mousePositionInWorld, mousePositionInArea);
        System.out.println("press");
    }

    @Override
    public void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseReleased(sprite, mousePositionInWorld, mousePositionInArea);
        System.out.println("released");
    }

    @Override
    public void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseClicked(sprite, mousePositionInWorld, mousePositionInArea);
        System.out.println("clicked");
    }

    @Override
    public void onMouseDragged(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseDragged(sprite, mousePositionInWorld, mousePositionInArea);
        System.out.println("dragged");
    }

    @Override
    public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseEnter(sprite, mousePositionInWorld, mousePositionInArea);
        System.out.println("enter");
    }

    @Override
    public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
        super.onMouseExit(sprite, mousePositionInWorld);
        System.out.println("exit");
    }
}
