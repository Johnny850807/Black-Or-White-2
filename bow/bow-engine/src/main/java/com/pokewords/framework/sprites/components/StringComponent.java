package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A immutable renderable component that can be rendered as a text
 * @author johnny850807 (waterball)
 */
public class StringComponent extends Component implements Shareable, Renderable {
    private String text;
    private List<StringFrame> stringFrame;

    private Sprite sprite;

    public StringComponent(String text) {
        this.text = text;
    }

    @Override
    public void onComponentInjected() {
        stringFrame = Collections.singletonList(new StringFrame(sprite, 0, text));
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return stringFrame;
    }

    @Override
    public List<StringFrame> getRenderedFrames() {
        return stringFrame;
    }


    public String getText() {
        return text;
    }

}
