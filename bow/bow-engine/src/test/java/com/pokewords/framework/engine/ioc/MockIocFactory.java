package com.pokewords.framework.engine.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
import com.pokewords.framework.sprites.components.frames.FrameFactory;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public class MockIocFactory implements IocFactory
{
    @Override
    public FrameFactory frameFactory() {
        return null;
    }

    @Override
    public ScriptTextParser scriptParser() {
        return null;
    }

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
}
