package com.pokewords.framework.sprites.components;


import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class Component implements AppStateLifeCycleListener {
    private @Nullable Sprite sprite;
    private @Nullable AppStateWorld world;

    @Override
    public void onAppStateCreate() {
        //hook
    }

    @Override
    public void onAppStateEnter() {
        //hook
    }

    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void onComponentAttachedWorld(AppStateWorld appStateWorld) {
        this.world = appStateWorld;
    }

    public void onComponentDetachedSprite(Sprite sprite) {
        this.sprite = null;
    }

    public void onComponentDetachedWorld(AppStateWorld appStateWorld) {
        this.world = null;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        //hook
    }

    @Override
    public void onAppStateExit() {
        //hook
    }

    @Override
    public void onAppStateDestroy() {
        //hook
    }

    public boolean hasOwnerSprite() {
        return sprite != null;
    }

    public boolean isAttachedToWorld() {
        return world != null;
    }

    public Sprite getOwnerSprite() {
        return Objects.requireNonNull(sprite, "The component is not attached to any Sprite.");
    }

    public AppStateWorld getAttachedWorld() {
        return Objects.requireNonNull(world, "The component is not attached to any world.");
    }

    public GameEngineFacade getGameEngineFacade() {
        return Objects.requireNonNull(world, "The component is not attached to any world," +
                "thus it can't get the GameEngineFacade.").getGameEngineFacade();
    }
}
