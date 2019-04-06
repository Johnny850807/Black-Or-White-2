package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.FrameFactory;
import com.pokewords.framework.engine.TextureFrameFactory;
import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.engine.asm.loader.scriptparser.PrototypeParser;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.views.SoundPlayer;

public class ReleaseIocFactory implements IocFactory{

    @Override
    public FrameFactory frameFactory() {
        return new TextureFrameFactory();
    }

    @Override
    public PrototypeParser prototypeParser() {
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
