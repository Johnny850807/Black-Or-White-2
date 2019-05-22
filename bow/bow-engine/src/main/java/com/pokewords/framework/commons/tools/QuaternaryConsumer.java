package com.pokewords.framework.commons.tools;

@FunctionalInterface
public interface QuaternaryConsumer<A, B, C, D> {
    void accept(A a, B b, C c, D d);
}
