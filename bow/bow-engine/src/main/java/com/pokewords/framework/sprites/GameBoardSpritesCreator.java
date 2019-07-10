package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.factories.SpriteInitializer;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Creator that builds a set of sprites where each sprite is fit in one
 * of a squared-grid of the board. A great example of using this creator is for
 * generating a GameMap, which consists of grids of tile-sprite.
 *
 * @author johnny850807 (waterball)
 */
public class GameBoardSpritesCreator {
    private SpriteInitializer spriteInitializer;

    public GameBoardSpritesCreator(SpriteInitializer spriteInitializer) {
        this.spriteInitializer = spriteInitializer;
    }

    public BuildingSession ofGridSize(int gridSize) {
        return this.new BuildingSession(gridSize);
    }

    public class BuildingSession {
        private int gridSize;
        private Map<Character, Object> symbolToSpriteTypeMap = new HashMap<>();
        private Collection<Sprite> sprites = new HashSet<>();

        public BuildingSession(int gridSize) {
            this.gridSize = gridSize;
        }

        public BuildingSession symbol(char symbol, Object spriteType) {
            symbolToSpriteTypeMap.put(symbol, spriteType);
            return this;
        }

        public BuildingSession board(String[] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length(); j++) {
                    char symbol = board[i].charAt(j);
                    Sprite sprite = spriteInitializer.createSprite(symbolToSpriteTypeMap.get(symbol));
                    sprite.setPosition(j * gridSize, i * gridSize);
                    sprite.setAreaSize(gridSize, gridSize);
                    sprites.add(sprite);
                }
            }
            return this;
        }

        public Collection<Sprite> build() {
            return sprites;
        }
    }
}
