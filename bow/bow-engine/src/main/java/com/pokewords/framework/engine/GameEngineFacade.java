package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.inputs.InputManager;

/**
 * @author johnny850807 (waterball)
 */
public class GameEngineFacade implements SoundPlayer {
    private IocContainer iocContainer;
    private SoundPlayer soundPlayer;
    private InputManager inputManager;
    private SpriteInitializer spriteInitializer;

    public GameEngineFacade(IocContainer iocContainer) {
        this.iocContainer = iocContainer;
        this.soundPlayer = iocContainer.soundPlayer();
        this.inputManager = iocContainer.inputManager();
        this.spriteInitializer = new SpriteInitializer(iocContainer);
    }


    @Override
    public void addSound(Object name, String soundPath) {
        soundPlayer.addSound(name, soundPath);
    }

    @Override
    public void removeSound(Object name) {
        soundPlayer.removeSound(name);
    }

    @Override
    public void playSound(Object name) {
        soundPlayer.playSound(name);
    }

    @Override
    public void playSoundLooping(Object name, int loop) {
        soundPlayer.playSoundLooping(name, loop);
    }

    @Override
    public void playSoundLoopingForever(Object name) {
        soundPlayer.playSoundLoopingForever(name);
    }

    @Override
    public void pause(Object name) {
        soundPlayer.pause(name);
    }

    @Override
    public void stop(Object name) {
        soundPlayer.stop(name);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public SpriteInitializer.SpriteDeclarator declare(Object type) {
        return spriteInitializer.declare(type);
    }

    public void setInitiazationMode(SpriteInitializer.InitializationMode mode) {
        spriteInitializer.setInitializationMode(mode);
    }

    public Sprite createSprite(Object type) {
        return spriteInitializer.createSprite(type);
    }

    public IocContainer getIocContainer() {
        return iocContainer;
    }
}
