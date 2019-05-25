package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.DataSaver;
import com.pokewords.framework.sprites.factories.*;
import com.pokewords.framework.views.helpers.galleries.DefaultGalleryFactory;
import com.pokewords.framework.views.helpers.galleries.GalleryFactory;
import com.pokewords.framework.views.inputs.DefaultInputManager;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.sound.SwingSoundPlayer;

public class ReleaseIocContainer implements IocContainer {
    private PrototypeFactory prototypeFactory;
    private SpriteBuilder spriteBuilder;
    private SoundPlayer soundPlayer;
    private InputManager inputManager;
    private GalleryFactory galleryFactory;
    private SpriteInitializer spriteInitializer;

    @Override
    public PrototypeFactory prototypeFactory() {
        return prototypeFactory == null ?
                prototypeFactory = new DefaultPrototypeFactory() : prototypeFactory;
    }

    @Override
    public SoundPlayer soundPlayer() {
        return soundPlayer == null ? soundPlayer = new SwingSoundPlayer() : soundPlayer;
    }

    @Override
    public DataSaver userConfig() {
        return null;
    }

    @Override
    public InputManager inputManager() {
        return inputManager == null ? inputManager = new DefaultInputManager() : inputManager;
    }

    @Override
    public SpriteBuilder spriteBuilder() {
        return spriteBuilder == null ?
                spriteBuilder = new DefaultSpriteBuilder(this) : spriteBuilder;
    }

    @Override
    public GalleryFactory galleryFactory() {
        return galleryFactory == null ? galleryFactory = new DefaultGalleryFactory() : galleryFactory;
    }

    @Override
    public SpriteInitializer spriteInitializer() {
        return spriteInitializer == null ?
                spriteInitializer = new SpriteInitializer(this) : spriteInitializer;
    }


}
