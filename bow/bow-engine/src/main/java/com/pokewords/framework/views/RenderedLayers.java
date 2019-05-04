package com.pokewords.framework.views;

import java.util.Collections;
import java.util.List;
import com.pokewords.framework.sprites.components.frames.Frame;

public class RenderedLayers {
	public List<List<Frame>> layers = Collections.EMPTY_LIST;

	public RenderedLayers(List<List<Frame>> layers) {
		this.layers = layers;
	}

	public List<List<Frame>> getLayers() {
		return layers;
	}

	public void setLayers(List<List<Frame>> layers) {
		this.layers = layers;
	}
}
