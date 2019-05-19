package com.pokewords.framework.sprites.components;

/**
 * @author johnny850807 (waterball)
 */
public abstract class ClickableComponent extends CloneableComponent {
    public Runnable onClickListener;

    public ClickableComponent(Runnable onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void fireOnClickEvent() {
        onClickListener.run();
    }
}
