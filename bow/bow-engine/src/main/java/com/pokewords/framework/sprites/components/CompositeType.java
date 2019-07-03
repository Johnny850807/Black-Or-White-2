
package com.pokewords.framework.sprites.components;

import com.pokewords.framework.commons.utils.StringUtility;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Composite type.
 * @author johnny850807 (waterball)
 */
public class CompositeType {
    private LinkedList<Object> types;

    /**
     * @param types a list of inheritance chain from the highest level type to the lowest (concrete) level
     */
    public CompositeType(Object ...types) {
        this.types = new LinkedList<>(Arrays.asList(types));
    }

    public void addParentType(Object type) {
        types.addFirst(type);
    }
    public void addSubtype(Object type) {
        types.addLast(type);
    }

    public void removeType(Object type) {
        types.remove(type);
    }

    public boolean isType(Object type) {
        return types.contains(type);
    }


    /**
     * @return the lowest level type
     */
    public Object getConcreteType() {
        return types.getLast();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeType that = (CompositeType) o;
        return types.equals(that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types);
    }

    @Override
    public String toString() {
        return StringUtility.toString(this.types);
    }
}
