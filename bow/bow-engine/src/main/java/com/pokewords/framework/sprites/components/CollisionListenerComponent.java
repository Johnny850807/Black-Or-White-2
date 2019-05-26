package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public class CollisionListenerComponent extends CloneableComponent {
    private Sprite sprite;
    private Listener listener;

    public static CollisionListenerComponent ofListener(Listener listener) {
        return new CollisionListenerComponent(listener);
    }

    private CollisionListenerComponent(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public interface Listener {
        void onCollision(Sprite thisSprite, Sprite thatSprite);
    }

    public void notifyCollision(Sprite thatSprite) {
        listener.onCollision(sprite, thatSprite);
    }
}
