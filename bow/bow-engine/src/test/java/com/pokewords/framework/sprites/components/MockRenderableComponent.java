package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MockRenderableComponent extends MockComponent implements Renderable{
    private LinkedList<Frame> mockFrames = new LinkedList<>();

    public void addFrame(Frame frame) {
        mockFrames.add(frame);
    }

    @Override
    public List<Frame> getCurrentFrames() {
        return mockFrames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockRenderableComponent that = (MockRenderableComponent) o;
        return mockFrames.equals(that.mockFrames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockFrames);
    }

}
