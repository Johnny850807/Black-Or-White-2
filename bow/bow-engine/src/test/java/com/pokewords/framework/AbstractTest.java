package com.pokewords.framework;

import com.pokewords.framework.engine.ioc.MockIocFactory;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;

public abstract class AbstractTest {
    protected IocFactory release = new ReleaseIocFactory();
    protected IocFactory mock = new MockIocFactory();

}