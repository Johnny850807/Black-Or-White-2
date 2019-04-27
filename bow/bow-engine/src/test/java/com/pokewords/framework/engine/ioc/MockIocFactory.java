package com.pokewords.framework.engine.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.parsing.ScriptTextParser;
import com.pokewords.framework.views.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public class MockIocFactory implements IocFactory
{
    @Override
    public FrameFactory frameFactory() {
        return null;
    }

    @Override
    public ScriptTextParser scriptTextParser() {
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
    public InputManager inputs() {
        return null;
    }
}
