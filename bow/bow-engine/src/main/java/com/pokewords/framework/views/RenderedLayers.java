package com.pokewords.framework.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.pokewords.framework.sprites.components.frames.Frame;

/**
 * @author Joanna
 */
public class RenderedLayers {
	private List<List<Frame>> layers;

	public RenderedLayers() {
	    layers = new ArrayList<>();
	}

	public RenderedLayers(List<List<Frame>> layers) {
		this.layers = layers;
	}

	public List<List<Frame>> getLayers() {
		return layers;
	}

	public void setLayers(List<List<Frame>> layers) {
		this.layers = layers;
	}

	public void clearEachLayer() {
		layers.forEach(List::clear);
	}

    /**
     * Add the frame to the rendered layer.
     * @param frame The Frame.
     * @param layerIndex The index of the frame in rendered layer.
     */
    public void addFrame(Frame frame, int layerIndex) {
        expandLayersToFitTheIndex(layerIndex);
        layers.get(layerIndex).add(frame);
    }

    private void expandLayersToFitTheIndex(int layerIndex) {
        IntStream.range(layers.size(), layerIndex+1)
                .forEach(i -> layers.add(new ArrayList<>()));
    }
}
