package com.pokewords.framework.commons.bundles;

/**
 * A read only version of bundle.
 * @author johnny850807 (waterball)
 */
public class ReadOnlyBundle extends Bundle {

    public ReadOnlyBundle() { }

    public ReadOnlyBundle(int eventId) {
        super(eventId);
    }

    @Override
    public void put(Object key, Object value) {
        throw new UnsupportedOperationException("The bundle is read-only.");
    }
}
