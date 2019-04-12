package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.TextureFrameFactory;
import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.parsing.FrameStateMachineScriptParser;
import com.pokewords.framework.views.SoundPlayer;

public class ReleaseIocFactory implements IocFactory{

    @Override
    public FrameFactory frameFactory() {
        return new TextureFrameFactory();
    }

    @Override
    public FrameStateMachineScriptParser frameStateMachineScriptParser() {

        // Return the sprites.components.

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
}
