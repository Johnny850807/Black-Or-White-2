package homework;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.MouseListenerComponent;
import com.pokewords.framework.sprites.components.StringComponent;

import java.awt.*;

public class TextMouseListener extends MouseListenerComponent.Listener {

    @Override
    public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        sprite.getComponent(StringComponent.class).setColor(new Color(255, 255, 255));
    }

    @Override
    public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
        sprite.getComponent(StringComponent.class).setColor(new Color(10, 10, 10));
    }
}
