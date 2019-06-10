package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.SpriteException;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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


	protected Sprite(String type) {
		this(new PropertiesComponent(type));
	}

	public Sprite(final PropertiesComponent propertiesComponent) {
		addComponent(propertiesComponent);
	}

	public Sprite(final Collection<Component> components) {
		components.forEach(this::addComponent);
	}

	public void attachToWorld(AppStateWorld world) {
		this.world = world;
		components.foreachCloneableComponent(c -> c.onComponentAttachedWorld(world));
	}

	public void detachFromWorld(AppStateWorld world) {
		assert this.world == world;
		components.foreachCloneableComponent(c -> c.onComponentDetachedWorld(world));
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
			getComponent(FrameStateMachineComponent.class).trigger(event);
		else
			throw new SpriteException("The sprite doesn't have the FrameStateMachineComponent, it cannot be triggered.");
	}

	public FrameStateMachineComponent getFrameStateMachineComponent() {
		return getComponent(FrameStateMachineComponent.class);
	}

	public PropertiesComponent getPropertiesComponent() {
		return getComponent(PropertiesComponent.class);
	}


	public boolean hasComponent(Class<? extends Component> type) {
		return components.containsComponent(type);
	}

	public boolean isRigidBody() {
		return hasComponent(RigidBodyComponent.class);
	}

	public <T extends Component> Optional<T> getComponentOptional(Class<T> type) {
		return hasComponent(type) ? Optional.of(getComponent(type)) : Optional.empty();
	}

	/**
	 * Get the component by the given class, the component returned will be the first one found
	 * whose type is assignable to the given class, i.e. the component's class is a subclass
	 * or is same to the given class.
	 * @param type the component's class
	 * @return the component's instance
	 */
	public <T extends Component> T getComponent(Class<T> type) {
		if (!hasComponent(type))
			throw new IllegalArgumentException("The sprite doesn't have component of the type " + type.getSimpleName() + ".");
		return type.cast(components.get(type));
	}

	/**
	 * Get all components whose type is assignable to the given type, i.e.  either is a subclass
	 * or is equal to the given type.
	 * @param type the component's class
	 * @return the component's instance
	 */
	public <T extends Component> Set<T> locateComponents(Class<T> type) {
		return components.locate(type);
	}

	/**
	 * Get the first component found whose type is assignable to the given type, i.e. either is a subclass
	 * or is equal to the given type.
	 * @param type the component's class
	 * @return the component's instance
	 */
	public <T extends Component> T locateFirstComponent(Class<T> type) {
		return components.locateFirst(type);
	}

	/**
	 * Add a component by its name.
	 * @param component the added component.
	 */
	public <T extends Component> void addComponent(@NotNull Component component) {
		components.put(component.getClass(), component);

		if (component instanceof CloneableComponent)
			((CloneableComponent)component).onComponentAttachedSprite(this);
	}

	/**
	 * Remove the component by name.
	 * @param type the type of the component
	 * @return the removed component if the name exist, null-object otherwise.
	 */
	public <T extends Component> void removeComponent(Class<T> type) {
		Component component = components.remove(type);
		if (component instanceof CloneableComponent)
			((CloneableComponent)component).onComponentDetachedSprite(this);
	}

	@Override
	public void onAppStateCreate(){
		for (Component component : components.values())
			component.onAppStateCreate();
	}

	@Override
	public void onUpdate(double timePerFrame) {
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

	public void addPositionChangedListener(PropertiesComponent.SpritePositionChangedListener positionChangedListener) {
		getPropertiesComponent().addPositionListener(positionChangedListener);
	}

	public void removePositionChangedListener(PropertiesComponent.SpritePositionChangedListener positionChangedListener) {
		getPropertiesComponent().removePositionChangedListener(positionChangedListener);
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

    public Dimension getBodySize() {
		return getPropertiesComponent().getBodySize();
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
        return getPropertiesComponent().getX();
    }

    public int getY() {
        return getPropertiesComponent().getY();
    }

    public void setArea(int x, int y, int width, int height) {
        getPropertiesComponent().setArea(x, y, width, height);
    }
    public void setArea(Rectangle area) {
		getPropertiesComponent().setArea(area);
	}

	public Dimension getAreaSize() {
		return getPropertiesComponent().getAreaSize();
	}

    public Rectangle getArea() {
		return getPropertiesComponent().getArea();
	}

	public void setAreaSize(Dimension size) {
		getPropertiesComponent().setAreaSize(size);
	}
    public int getWidth() {
        return getPropertiesComponent().getWidth();
    }

    public int getHeight() {
        return getPropertiesComponent().getHeight();
    }

    public CompositeType getType() {
        return getPropertiesComponent().getType();
    }

    public Object getConcreteType() {
		return getPropertiesComponent().getConcreteType();
	}

    public Point getCenter() {
        return getPropertiesComponent().getCenter();
    }

    public void setCenter(int x, int y) {
        getPropertiesComponent().setCenter(x, y);
    }

    public void setCenter(Point point) {
        getPropertiesComponent().setCenter(point);
    }

	public void move(Point point) {
		getPropertiesComponent().move(point);
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

	public boolean isType(Object type) {
		return  getPropertiesComponent().isType(type);
	}

	public boolean anyType(Object ...types) {
	    return getPropertiesComponent().anyType(types);
    }


	public void resumeToLatestPosition() {
		getPropertiesComponent().resumeToLatestPosition();
	}

	public PropertiesComponent.LatestStates getLatestProperties() {
		return getPropertiesComponent().getLatestStates();
	}

	public Sprite clone(){
		try {
			Sprite clone = (Sprite) super.clone();
			clone.components = this.components.clone();
			clone.components.foreachCloneableComponent((c) -> c.onComponentAttachedSprite(clone));
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Make the components injected.
	 * @see ComponentInjector#inject(Sprite)
	 * @deprecated
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

	public Collection<CloneableComponent> getCloneableComponents() {
		return components.getCloneableComponents();
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
		StringBuilder stringBuilder = new StringBuilder("** Sprite (").append(getType()).append(") @").append(super.toString());
		for (Class componentType : components.keySet()) {
			stringBuilder.append("\n**** ").append(componentType.getSimpleName()).append(" :\n");
			stringBuilder.append(components.get(componentType));
		}
		return stringBuilder.toString();
	}
}
