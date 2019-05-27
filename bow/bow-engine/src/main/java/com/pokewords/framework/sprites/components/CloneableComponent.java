package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * CloneableComponent owns the clone() method (shallow clone defaulted) and it's marked with the Cloneable interface.
 *
 * Note that your CloneableComponent should overwrite the clone() method with the deep-clone version if you need.
 * If you have a reference type or have a Frame as a member in your CloneableComponent ,
 * you gotta overwrite it with a deep clone implementation.
 *
 * @author johnny850807 (waterball)
 */
public abstract class CloneableComponent extends Component implements Cloneable {
    private @Nullable Sprite sprite;
    private @Nullable AppStateWorld world;

    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void onComponentAttachedWorld(AppStateWorld appStateWorld) {
        this.world = appStateWorld;
    }

    public void onComponentDetachedSprite(Sprite sprite) {
        assert this.sprite == sprite;
        this.sprite = null;
    }

    public void onComponentDetachedWorld(AppStateWorld appStateWorld) {
        assert this.world == appStateWorld;
        this.world = null;
    }

    public CloneableComponent clone(){
        try {
            return (CloneableComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


    public Sprite getOwnerSprite() {
        return Objects.requireNonNull(sprite, "The component is not attached to any Sprite.");
    }

    public AppStateWorld getAttachedWorld() {
        return Objects.requireNonNull(world, "The component is not attached to any world.");
    }

    public boolean hasOwnerSprite() {
        return sprite != null;
    }

    public boolean isAttachedToWorld() {
        return world != null;
    }
}
