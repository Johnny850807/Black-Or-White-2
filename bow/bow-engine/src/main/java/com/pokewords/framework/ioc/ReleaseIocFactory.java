package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.sprites.DefaultPrototypeFactory;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.TextureFrameFactory;
import com.pokewords.framework.views.DefaultInputManager;
import com.pokewords.framework.views.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public class ReleaseIocFactory implements IocFactory{
    private TextureFrameFactory textureFrameFactory;
    private PrototypeFactory prototypeFactory;

    @Override
    public FrameFactory frameFactory() {
        return textureFrameFactory == null ?
                textureFrameFactory = new TextureFrameFactory() : textureFrameFactory;
    }

    @Override
    public PrototypeFactory prototypeFactory() {
        return prototypeFactory == null ?
                prototypeFactory = new DefaultPrototypeFactory() : prototypeFactory;
    }

    @Override
    public SoundPlayer soundPlayer() {
        return null;
    }

    @Override
    public UserConfig userConfig() {
        return null;
    }

    @Override
    public InputManager inputManager() {
        return new DefaultInputManager();
    }
}
