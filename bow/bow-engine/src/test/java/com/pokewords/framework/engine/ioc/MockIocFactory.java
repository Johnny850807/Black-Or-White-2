package com.pokewords.framework.engine.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
import com.pokewords.framework.sprites.factories.SpriteBuilder;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.inputs.InputManager;

public class MockIocFactory implements IocFactory {
    @Override
    public PrototypeFactory prototypeFactory() {
        return null;
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
        return null;
    }

    @Override
    public SpriteBuilder spriteBuilder() {
        return null;
    }
}
