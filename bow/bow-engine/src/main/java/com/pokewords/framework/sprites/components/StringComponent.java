package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StringComponent extends Component implements Shareable, Renderable {
    private List<StringFrame> stringFrame;

    public StringComponent(String text) {
        stringFrame = Collections.singletonList(new StringFrame(text));
    }

    @Override
    public void onAppStateCreate(AppStateWorld world) { }

    @Override
    public void onAppStateEnter() { }

    @Override
    public void onAppStateExit() { }

    @Override
    public void onAppStateDestroy() { }

    @Override
    public void onUpdate(int timePerFrame) { }

    public StringFrame getStringFrame() {
        return stringFrame.get(0);
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return stringFrame;
    }

    @Override
    public List<StringFrame> getRenderedFrames() {
        return stringFrame;
    }
}
