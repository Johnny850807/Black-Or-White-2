package com.pokewords.framework.views;

import java.util.*;

import com.pokewords.framework.commons.NullIterator;
import com.pokewords.framework.sprites.components.frames.Frame;
import org.jetbrains.annotations.NotNull;

/**
 * @author johnny850807 (waterball)
 */
public class RenderedLayers implements Iterable<Frame> {
	private Map<Integer, Collection<Frame>> layers = new TreeMap<>();


	public Map<Integer, Collection<Frame>> getLayers() {
		return layers;
	}

	public void clear() {
		layers.clear();
	}

	public void clearEachLayer() {
		layers.values().forEach(Collection::clear);
	}

    /**
     * Add the frame to the rendered layer.
     * @param frame The Frame.
     */
    public void addFrame(Frame frame) {
    	if (!layers.containsKey(frame.getLayerIndex()))
    		layers.put(frame.getLayerIndex(), new LinkedList<>());
		layers.get(frame.getLayerIndex()).add(frame);
    }

    public void addFrames(Collection<? extends Frame> frames) {
    	frames.forEach(this::addFrame);
	}

	public Collection<Frame> getLayer(int layerIndex) {
    	return layers.get(layerIndex);
	}

	@NotNull
	@Override
	public Iterator<Frame> iterator() {
		return new LayersIterator();
	}

	private class LayersIterator implements Iterator<Frame> {
		private Iterator<Collection<Frame>> layersIterator;
		private Iterator<Frame> currentLayerIterator;

		public LayersIterator() {
			layersIterator = layers.values().iterator();
			currentLayerIterator = layersIterator.hasNext() ? layersIterator.next().iterator() : new NullIterator<>();
		}

		@Override
		public boolean hasNext() {
			return currentLayerIterator.hasNext() || findNextNonEmptyLayer();
		}

		private boolean findNextNonEmptyLayer() {
			if (layersIterator.hasNext())
			{
				currentLayerIterator = layersIterator.next().iterator();
				return currentLayerIterator.hasNext() || findNextNonEmptyLayer();
			}
			return false;
		}

		@Override
		public Frame next() {
			return currentLayerIterator.next();
		}
	}
}
