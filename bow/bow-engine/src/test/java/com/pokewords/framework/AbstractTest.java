package com.pokewords.framework.sprites;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;

public abstract class AbstractTest {
    protected IocFactory iocFactory = new ReleaseIocFactory();
    
}
