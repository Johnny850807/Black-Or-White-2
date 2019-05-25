package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

/**
 * @author johnny850807 (waterball)
 */
public class GameEngineFacade implements SoundPlayer {
    private IocContainer iocContainer;
    private SoundPlayer soundPlayer;
    private SpriteInitializer spriteInitializer;
    private GameWindowsConfigurator gameWindowsConfigurator;
    private GameEngine gameEngine;

    public GameEngineFacade(IocContainer iocContainer, GameEngine gameEngine, GameWindowsConfigurator gameWindowsConfigurator) {
        this.iocContainer = iocContainer;
        this.soundPlayer = iocContainer.soundPlayer();
        this.spriteInitializer = new SpriteInitializer(iocContainer);
        this.gameEngine = gameEngine;
        this.gameWindowsConfigurator = gameWindowsConfigurator;
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

    public SpriteInitializer.SpriteDeclarator declare(Object type) {
        return spriteInitializer.declare(type);
    }

    public void setInitiazationMode(SpriteInitializer.InitializationMode mode) {
        spriteInitializer.setInitializationMode(mode);
    }

    public Sprite createSprite(Object type) {
        return spriteInitializer.createSprite(type);
    }

    public GameWindowsConfigurator getGameWindowsConfigurator() {
        return gameWindowsConfigurator;
    }

    public IocContainer getIocContainer() {
        return iocContainer;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
