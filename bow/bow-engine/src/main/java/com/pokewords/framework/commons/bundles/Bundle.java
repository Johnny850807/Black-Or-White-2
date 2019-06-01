package com.pokewords.framework.commons.bundles;

import com.pokewords.framework.engine.exceptions.ExpectedPropertyMissingException;
import com.sun.corba.se.impl.io.TypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Bundle used for passing message between two AppState via transition.
 * @author johnny850807 (waterball)
 */
public class Bundle {
    public final static int NO_EVENT = -999999999;
    protected int eventId;
    protected Map<Object, Object> data;

    public Bundle() {
        this(NO_EVENT);
    }

    public Bundle(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void put(Object key, Object value) {
        lazyInitMap();
        data.put(key, value);
    }

    public int getInt(Object key) {
        if (!containsKey(key))
            throw new ExpectedPropertyMissingException(key);
        Object value = data.get(key);
        if (value instanceof String)
            return Integer.parseInt((String) value);
        throw new TypeMismatchException("The value is of type " + value.getClass().getSimpleName() + ", cannot convert it into int.");
    }

    public String getString(Object key) {
        if (!containsKey(key))
            throw new ExpectedPropertyMissingException(key);
        return String.valueOf( data.get(key));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        if (!containsKey(key))
            throw new ExpectedPropertyMissingException(key);
        return (T) data.get(key);
    }

    public <T> Optional<T> getOptional(Object key) {
        return containsKey(key) ? Optional.of(get(key)) : Optional.empty();
    }

    public OptionalInt getIntOptional(Object key) {
        return containsKey(key) ? OptionalInt.of(getInt(key)) : OptionalInt.empty();
    }

    public Optional<String> getStringOptional(Object key) {
        return containsKey(key) ? Optional.of(getString(key)) : Optional.empty();
    }

    public boolean containsKey(Object key) {
        lazyInitMap();
        return data.containsKey(key);
    }

    public boolean containsValue(Object value) {
        lazyInitMap();
        return data.containsValue(value);
    }

    private void lazyInitMap() {
        if (data == null) {
            data = new HashMap<>();
        }
    }

}
