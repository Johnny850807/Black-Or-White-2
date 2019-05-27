package com.pokewords.framework.commons;

import java.util.Iterator;

/**
 * The null Iterator always return false in hasNext()
 * @author johnny850807 (waterball)
 */
public class NullIterator<T> implements Iterator<T> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new UnsupportedOperationException("This method should never be invoked because it always return false in hasNext() method.");
    }
}
