package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.sprites.factories.DefaultSpriteBuilder;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
import com.pokewords.framework.sprites.factories.DefaultPrototypeFactory;
import com.pokewords.framework.sprites.factories.SpriteBuilder;
import com.pokewords.framework.views.inputs.DefaultInputManager;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public class ReleaseIocFactory implements IocFactory{
    private PrototypeFactory prototypeFactory;

    @Override
    public PrototypeFactory prototypeFactory() {
        return prototypeFactory == null?
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

    @Override
    public SpriteBuilder spriteBuilder() {
        return new DefaultSpriteBuilder(this);
    }
}
