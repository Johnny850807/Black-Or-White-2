package com.pokewords.framework;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author johnny850807 (waterball)
 */
public abstract class AbstractTest {
    protected IocFactory release = new ReleaseIocFactory();

    protected void assertNotSameButEquals(Object o1, Object o2){
        assertNotSame(o1, o2);
        assertEquals(o1, o2);
    }

    protected void assertDeepNotSameButEquals(List o1, List o2){
        if (o1.size() != o2.size())
            fail();

        assertNotSame(o1, o2);

        for (int i = 0; i < o1.size(); i++) {
            assertNotSame(o1.get(i), o2.get(i));
            assertEquals(o1.get(i), o2.get(i));
        }
    }

    protected void assertDeepNotSameButEquals(Map o1, Map o2){
        if (o1.size() != o2.size())
            fail();

        assertNotSame(o1, o2);

        for (Object key : o1.keySet()) {
            if (!o2.containsKey(key))
                fail();
            assertNotSame(o1.get(key), o2.get(key));
            assertEquals(o1.get(key), o2.get(key));
        }
    }
}
