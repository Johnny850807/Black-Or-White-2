package com.pokewords.framework.commons;

import java.util.Objects;

public class Triple<X, Y, Z> {
    private X first;
    private Y second;
    private Z third;

    private Triple(){}

    public Triple(X first, Y second, Z third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }


    public X getFirst() {
        return first;
    }

    public void setFirst(X first) {
        this.first = first;
    }

    public Y getSecond() {
        return second;
    }

    public void setSecond(Y second) {
        this.second = second;
    }

    public Z getThird() {
        return third;
    }

    public void setThird(Z third) {
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return first.equals(triple.first) &&
                second.equals(triple.second) &&
                third.equals(triple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString() {
        return String.format("<%s, %s, %s>", first, second, third);
    }
}
