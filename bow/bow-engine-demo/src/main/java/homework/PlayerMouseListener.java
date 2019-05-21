package homework;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class PlayerMouseListener implements MouseListenerComponent.Listener {

    @Override
    public void onMouseDragged(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        sprite.setPosition(mousePositionInWorld);
    }
}
