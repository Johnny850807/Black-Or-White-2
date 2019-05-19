package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.SpriteException;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * A sprite is the fundamental unit in AppStateGameWorld.
 *  - It's guaranteed to have a propertiesComponent which records the sprite's body, type, ... and so one.
 *  - When the engine renders the sprite, it actually queries all of the sprite's RenderableComponents.
 *  - FrameStateMachineComponent is on of the common RenderableComponents.
 *  - FrameStateMachineComponent uses the Finite State Machine to represent the sprite's states, and decides
 *  what to be rendered in each state. use Sprite#trigger(event) to instruct the FrameStateMachineComponent.
 *
 * @author johnny850807, nyngwang
 */
public class Sprite implements Cloneable, AppStateLifeCycleListener {
	protected @Nullable AppStateWorld world;
	protected ComponentMap components = new ComponentMap();
	private double timePerFrame;


	protected Sprite(String type) {
		this(new PropertiesComponent(type));
	}

	public Sprite(final PropertiesComponent propertiesComponent) {
		addComponent(propertiesComponent);
	}

	public Sprite(final Collection<Component> components) {
		components.forEach(this::addComponent);
	}

	public void setWorld(@Nullable AppStateWorld world) {
		this.world = world;
	}

	@Nullable
	public AppStateWorld getWorld() {
		return world;
	}

	/**
	 * @return all components the sprite owns.
	 */
	public Collection<Component> getComponents() {
		return components.values();
	}

	/**
	 * trigger an event to the sprite's frameStateMachineComponent, if the sprite doesn't have
	 * the frameStateMachineComponent, SpriteException thrown
	 */
	public void trigger(Object event) throws SpriteException {
		if (hasComponent(FrameStateMachineComponent.class))
			getComponent(FrameStateMachineComponent.class).trigger(event.toString());
		else
			throw new SpriteException("The sprite doesn't have the FrameStateMachineComponent, it cannot be triggered.");
	}

	public FrameStateMachineComponent getFrameStateMachineComponent() {
		return getComponent(FrameStateMachineComponent.class);
	}

	public PropertiesComponent getPropertiesComponent() {
		return getComponent(PropertiesComponent.class);
	}

	public CollidableComponent getCollidableComponent() {
		return getComponent(CollidableComponent.class);
	}

	public ClickableComponent getClickableComponent() {
		return getComponent(ClickableComponent.class);
	}

	public boolean isCollidable() {
		return hasComponent(CollidableComponent.class);
	}

	public boolean hasComponent(Class<? extends Component> type) {
		return components.containsKey(type);
	}

	/**
	 * Get the component.
	 * @param type the component's class
	 * @return the component's instance if exists, otherwise null.
	 */
	public <T extends Component> T getComponent(Class<T> type) {
		return type.cast(components.get(type));
	}

	/**
	 * Add a component by its name.
	 * @param component the added component.
	 */
	public <T extends Component> void addComponent(@NotNull Component component) {
		components.put(component.getClass(), component);
		component.onComponentAdded();
		ComponentInjector.inject(this, component);
	}

	/**
	 * Remove the component by name.
	 * @param type the type of the component
	 * @return the removed component if the name exist, null-object otherwise.
	 */
	public <T extends Component> void removeComponent(Class<T> type) {
		components.remove(type).onComponentRemoved();
	}

	@Override
	public void onAppStateCreate(){
		for (Component component : components.values())
			component.onAppStateCreate();
	}

	@Override
	public void onUpdate(double timePerFrame) {
		this.timePerFrame = timePerFrame;
        for (Component component : components.values())
            component.onUpdate(timePerFrame);
	}

	@Override
	public void onAppStateEnter() {
		for (Component component : components.values())
			component.onAppStateEnter();
	}

    @Override
    public void onAppStateExit() {
		for (Component component : components.values())
			component.onAppStateExit();
    }

    @Override
	public void onAppStateDestroy() {
		for (Component component : components.values())
			component.onAppStateDestroy();
	}

	public void setBody(int x, int y, int w, int h) {
        getPropertiesComponent().setBody(x, y, w, h);
    }

    public void setBody(Rectangle body) {
        getPropertiesComponent().setBody(body);
    }

    public Rectangle getBody() {
        return getPropertiesComponent().getBody();
    }

    public void setPosition(Point position) {
        getPropertiesComponent().setPosition(position);
    }

    public void setPosition(int x, int y) {
        getPropertiesComponent().setPosition(x, y);
    }

    public Point getPosition() {
        return getPropertiesComponent().getPosition();
    }

    public int getX() {
        return (int) getPosition().getX();
    }

    public int getY() {
        return (int) getPosition().getY();
    }

    public int getWidth() {
        return (int) getBody().getWidth();
    }

    public int getHeight() {
        return (int) getBody().getHeight();
    }

    public void setType(String type) {
        getPropertiesComponent().setType(type);
    }

    public Object getType() {
        return getPropertiesComponent().getType();
    }

    public Point2D getCenter() {
        return getPropertiesComponent().getCenter();
    }

    public void setCenter(int x, int y) {
        getPropertiesComponent().setCenter(x, y);
    }

    public void setCenter(Point point) {
        getPropertiesComponent().setCenter(point);
    }

    public void move(int velocityX, int velocityY) {
		getPropertiesComponent().move(velocityX, velocityY);
	}
    public void moveX(int velocityX) {
		getPropertiesComponent().moveX(velocityX);
	}

	public void moveY(int velocityY) {
		getPropertiesComponent().moveY(velocityY);
	}

	public boolean isType(Object obj) {
		return getType().equals(obj.toString());
	}

	public Sprite clone(){
		try {
			Sprite clone = (Sprite) super.clone();
			clone.components = this.components.clone();
			clone.injectComponents();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Make the components injected.
	 * @see ComponentInjector#inject(Sprite)
	 */
	protected void injectComponents(){
		ComponentInjector.inject(this);
	}


	/**
	 * @return all the frames that should be rendered during the present state.
	 */
	public Collection<? extends Frame> getRenderedFrames() {
		return components.getRenderedFrames();
	}

	/**
	 * @return all components marked by Renderable interface
	 * @see Renderable
	 */
	public Collection<Renderable> getRenderableComponents() {
		return components.getRenderableComponents();
	}


	/**
	 * @return all components not marked by Shareable interface
	 * @see Shareable
	 */
	public Set<Component> getNonshareableComponents(){
		return components.getNonshareableComponents();
	}

	/**
	 * @return all components marked by Shareable interface
	 * @see Shareable
	 */
	public Set<Component> getShareableComponents(){
		return components.getShareableComponents();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Sprite sprite = (Sprite) o;
		return Objects.equals(components, sprite.components);
	}

	@Override
	public int hashCode() {
		return Objects.hash(components);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("----- Sprite(").append(getType()).append("-----");
		for (Class componentType : components.keySet()) {
			stringBuilder.append("\n== Component: ").append(componentType.getSimpleName());
			stringBuilder.append(" ==\n").append(components.get(componentType));
		}
		return stringBuilder.toString();
	}
}
