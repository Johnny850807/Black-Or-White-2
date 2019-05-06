package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class StringsComponent extends Component implements Shareable, Renderable {
    private LinkedList<StringFrame> stringFrames = new LinkedList<>();

    public void addString(Point position, String text) {
        this.stringFrames.add(new StringFrame(position, text));
    }

    public void addString(int x, int y,  String text) {
        this.stringFrames.add(new StringFrame(x, y, text));
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
        return stringFrames;
    }
}
