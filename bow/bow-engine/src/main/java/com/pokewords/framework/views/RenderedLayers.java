package com.pokewords.framework.views;

import java.util.List;
import com.pokewords.framework.sprites.components.Frame;

public class RenderedLayers {

	public List<List<Frame>> layers;

	public List<List<Frame>> getLayers() {
		return layers;
	}

	public void setLayers(List<List<Frame>> layers) {
		this.layers = layers;
	}
}
