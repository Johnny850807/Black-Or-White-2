package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.util.Collections;
import java.util.List;

public class StringComponent extends Component implements Shareable, Renderable {
    private List<StringFrame> stringFrame;

    public StringComponent(int x, int y, String text) {
        stringFrame = Collections.singletonList(new StringFrame(x, y, text));
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

    @Override
    public List<StringFrame> getRenderedFrames() {
        return stringFrame;
    }
}
