package com.pokewords.framework.commons;

@FunctionalInterface
public interface TernaryConsumer<X, Y, Z> {

    void accept(X x, Y y, Z z);
}
