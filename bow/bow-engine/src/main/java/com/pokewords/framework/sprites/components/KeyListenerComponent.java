package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

public class KeyListenerComponent extends CloneableComponent {
    private Sprite sprite;
    private Listener listener;

    public KeyListenerComponent(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public interface Listener {
        default void onKeyPressed(Sprite sprite, int keyCode) {}
        default void onKeyReleased(Sprite sprite, int keyCode) {}
        default void onKeyClicked(Sprite sprite, int keyCode) {}
    }

    public Listener getListener() {
        return listener;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
