package com.pokewords.framework;

import com.pokewords.framework.engine.ioc.MockIocFactory;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public abstract class AbstractTest {
    protected IocFactory release = new ReleaseIocFactory();
    protected IocFactory mock = new MockIocFactory();


    /**
     * Use this method to test if two objects are the duplicates to each other
     * (Not at the same memory locations but have the same attributes)
     */
    protected void assertNotSameButEquals(Object o1, Object o2){
        assertNotSame(o1, o2);
        assertEquals(o1, o2);
    }
}
