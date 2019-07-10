package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.ImageFrameFactory;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The rendered component which consists of a board of grids which each
 * grid is a squared image frame. This is used to compose a game map if the game map
 * is squared-tile-based and every tile has a same size.
 * @author johnny850807 (waterball)
 */
public class BoardComponent extends CloneableComponent implements Renderable {
    private Collection<ImageFrame> allFrames;

    public BoardComponent() {
        this.allFrames = new LinkedList<>();
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return allFrames;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return allFrames;
    }

    public static class Builder {
        private BoardComponent instance;
        private int frameSize;
        private Map<Character, ImageFrame> symbolMap = new HashMap<>();

        private Builder(int frameSize) {
            this.frameSize = frameSize;
            instance = new BoardComponent();
        }

        public static Builder ofFrameSize(int frameSize) {
            return new Builder(frameSize);
        }

        public Builder symbol(char symbol, int layerIndex, String imagePath) {
            symbolMap.put(symbol, ImageFrameFactory.fromPath(layerIndex, imagePath));
            return this;
        }

        public Builder board(String[] board) {
            for (int i = 0; i < board.length; i ++) {
                for (int j = 0; j < board[i].length(); j ++) {
                    char symbol = board[i].charAt(j);
                    ImageFrame imageFrame = symbolMap.getOrDefault(symbol,
                            ImageFrameFactory.emptyImageFrame());
                    imageFrame.setPosition(new Point(i*frameSize, j*frameSize));
                    imageFrame.setSize(new Dimension(frameSize, frameSize));
                    instance.allFrames.add(imageFrame);
                }
            }
            return this;
        }

        public BoardComponent build() {
            return instance;
        }
    }


}
