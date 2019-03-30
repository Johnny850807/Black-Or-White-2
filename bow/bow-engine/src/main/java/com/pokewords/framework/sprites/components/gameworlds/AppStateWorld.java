package com.pokewords.framework.sprites.components.gameworlds;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.RenderedLayers;
import java.util.List;

public class AppStateWorld {

	private List<Sprite> sprites;

	private List<CollisionHandler> collisionHandlers;

	public void spawn(Sprite sprite) {

	}

	public RenderedLayers getRenderedLayers() {
		return null;
	}

	public void onUpdate() {

	}

	public void addCollisionHandler(CollisionHandler ch) {

	}

	public List<Sprite> getSprites() {
		return sprites;
	}

	public List<CollisionHandler> getCollisionHandlers() {
		return collisionHandlers;
	}

	public void setSprites(List<Sprite> sprites) {
		this.sprites = sprites;
	}

	public void setCollisionHandlers(List<CollisionHandler> collisionHandlers) {
		this.collisionHandlers = collisionHandlers;
	}
}
