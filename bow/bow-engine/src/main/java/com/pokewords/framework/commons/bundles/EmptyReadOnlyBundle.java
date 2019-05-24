package com.pokewords.framework.commons.bundles;

/**
 * An empty read only bundle.
 * @author johnny850807 (waterball)
 */
public class EmptyReadOnlyBundle extends ReadOnlyBundle {
    private static EmptyReadOnlyBundle instance = new EmptyReadOnlyBundle();

    private EmptyReadOnlyBundle() {}

    public static EmptyReadOnlyBundle getInstance() {
        return instance;
    }

    @Override
    public int getInt(Object key) {
        throw new UnsupportedOperationException("This is an empty bundle.");
    }

    @Override
    public String getString(Object key) {
        throw new UnsupportedOperationException("This is an empty bundle.");
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }
}
