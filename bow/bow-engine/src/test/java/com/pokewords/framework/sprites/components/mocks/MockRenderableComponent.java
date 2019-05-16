package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author johnny850807 (waterball)
 */
public class MockRenderableComponent extends MockComponentImp implements Renderable {
    private LinkedList<Frame> mockFrames = new LinkedList<>();

    public void addFrame(Frame frame) {
        mockFrames.add(frame);
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return mockFrames;
    }

    @Override
    public List<Frame> getRenderedFrames() {
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
