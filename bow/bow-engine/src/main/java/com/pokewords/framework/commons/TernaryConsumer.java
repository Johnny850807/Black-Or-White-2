package com.pokewords.framework.commons;

@FunctionalInterface
public interface TernaryConsumer<X, Y, Z> {

    void accpet(X x, Y y, Z z);
}
