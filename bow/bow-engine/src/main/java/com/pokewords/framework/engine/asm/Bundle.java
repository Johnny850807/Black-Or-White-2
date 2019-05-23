package com.pokewords.framework.engine.asm;

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
    private int eventId;
    private Map<Object, Object> data;

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
        lazyInitMap();
        return (int) data.get(key);
    }

    public String getString(Object key) {
        lazyInitMap();
        return String.valueOf( data.get(key));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        lazyInitMap();
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
        if (data == null)
            data = new HashMap<>();
    }

}
