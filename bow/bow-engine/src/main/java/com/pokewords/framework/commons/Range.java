package com.pokewords.framework.commons;

import java.util.Objects;

public class Range {
    private int start;
    private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return start == range.start &&
                end == range.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean within(int num) {
        return getStart() <= num && num <= getEnd();
    }

    public boolean intersect(Range range) {
        return range.within(this.end) || this.within(range.end);
    }
}
