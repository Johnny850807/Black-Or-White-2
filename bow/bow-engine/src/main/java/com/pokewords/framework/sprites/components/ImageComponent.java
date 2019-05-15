package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.utils.ImageUtility;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A immutable renderable component that can be rendered as a image.
 * @author johnny850807 (waterball)
 */
public class ImageComponent extends Component implements Shareable, Renderable {
    private Image image;
    private int layerIndex;
    private List<ImageFrame> imageFrame;

    private Sprite sprite;

    public ImageComponent(String imagePath, int layerIndex) {
        this.layerIndex = layerIndex;
        this.image = ImageUtility.readImage(imagePath);
    }

    public ImageComponent(Image image, int layerIndex) {
        this.layerIndex = layerIndex;
        this.image = image;
    }

    @Override
    public void onComponentInjected() {
        imageFrame = Collections.singletonList(new ImageFrame(sprite, 0, layerIndex, image));
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return imageFrame;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return imageFrame;
    }
}
