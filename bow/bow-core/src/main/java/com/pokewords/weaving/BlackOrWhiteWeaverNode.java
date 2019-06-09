package com.pokewords.weaving;

import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Script;

public class BlackOrWhiteWeaverNode implements SpriteWeaver.Node {
    @Override
    public void onWeaving(Script script, Sprite sprite, IocContainer iocContainer) {

    }
}
