package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.engine.exceptions.MandatoryComponentRequiredException;
import com.pokewords.framework.engine.exceptions.SpriteException;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A sprite is the fundamental unit in AppStateGameWorld.
 *  - It's guaranteed to have a propertiesComponent which records the sprite's body, type, ... and so one.
 *  - When the engine renders the sprite, it actually queries all of the sprite's RenderableComponents.
 *  - FrameStateMachineComponent is on of the common RenderableComponents.
 *  - FrameStateMachineComponent uses the Finite State Machine to represent the sprite's states, and decides
 *  what to be rendered in each state. use Sprite#trigger(event) to instruct the FrameStateMachineComponent.
 *
 * @see AppStateWorld
 * @see FrameStateMachineComponent
 * @see PropertiesComponent
 * @author johnny850807, nyngwang
 */
public class Sprite implements Cloneable, AppStateLifeCycleListener {
	protected AppStateWorld world;
	protected Map<String, Component> components = new HashMap<>();
	private Collection<Renderable> renderableComponents = new HashSet<>();
	private FrameStateMachineComponent frameStateMachineComponent;
	private PropertiesComponent propertiesComponent;
	private int timePerFrame;


	protected Sprite(String type) {
		this(new PropertiesComponent(type));
	}

	public Sprite(final PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
	}

	/**
	 * @return all components the sprite owns.
	 */
	public Collection<Component> getComponents() {
		return components.values();
	}

	/**
	 * trigger a event to the sprite's frameStateMachineComponent, if the sprite doesn't have
	 * the frameStateMachineComponent, SpriteException thrown
	 */
	public void trigger(Object event) throws SpriteException {
		if (frameStateMachineComponent != null)
			frameStateMachineComponent.trigger(event.toString());
		else
			throw new SpriteException("The sprite doesn't have the FrameStateMachineComponent, it cannot be triggered.");
	}

	/**
	 * The getter of FrameStateMachineComponent.
	 * @return The concrete FrameStateMachineComponent.
	 */
	public FrameStateMachineComponent getFrameStateMachineComponent() {
		return frameStateMachineComponent;
	}

	/**
	 * The getter of PropertiesComponent.
	 * @return The concrete PropertiesComponent.
	 */
	public PropertiesComponent getPropertiesComponent() {
		return propertiesComponent;
	}


	public boolean hasComponent(String name) {
		return components.containsKey(name);
	}

	/**
	 * Get the component by name.
	 * @param name the name of the component to get.
	 * @return the component to get if the name exist, null-object otherwise.
	 */
	public Component getComponentByName(String name) {
		return components.get(name);
	}

	/**
	 * Put new component with name. If the component you put is
	 * PropertiesComponent or FrameStateMachineComponent then this method will detect it.
	 * @param name the name of the component to be added.
	 * @param component the component to be added.
	 */
	public void putComponent(@NotNull String name, @NotNull Component component) {
		if (component instanceof Renderable)
			this.renderableComponents.add((Renderable) component);
		if (component instanceof PropertiesComponent)
			throw new SpriteException("PropertiesComponent's already set during the sprite's initiation, " +
					"you can't override it with another one.");
		else if (component instanceof FrameStateMachineComponent)
			this.frameStateMachineComponent = (FrameStateMachineComponent) component;
		components.put(name, component);
	}

	/**
	 * Remove the component by name.
	 * @param name the name of the component to be removed.
	 * @return the removed component if the name exist, null-object otherwise.
	 */
	public void removeComponentByName(String name) {
		if (!hasComponent(name))
			throw new SpriteException("FrameStateMachineComponent cannot be removed.");

		Component component = components.get(name);
		if (component instanceof PropertiesComponent)
			throw new MandatoryComponentRequiredException("PropertiesComponent cannot be removed.");

		components.remove(name);
	}

	@Override
	public void onAppStateCreate(AppStateWorld world){
		this.world = world;
		for (Component component : components.values())
			component.onAppStateCreate(world);
	}

	@Override
	public void onUpdate(int timePerFrame) {
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

    public int getW() {
        return (int) getBody().getWidth();
    }

    public int getH() {
        return (int) getBody().getHeight();
    }

    public void setType(String type) {
        getPropertiesComponent().setType(type);
    }

    public String getType() {
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
		getPosition().translate(velocityX*timePerFrame, velocityY*timePerFrame);
	}
    public void moveX(int velocityX) {
		getPosition().translate(velocityX*timePerFrame, 0);
	}

	public void moveY(int velocityY) {
		getPosition().translate(0, velocityY*timePerFrame);
	}

	public boolean isType(Object obj) {
		return getType().equals(obj.toString());
	}

	public Sprite clone(){
		try {
			Sprite clone = (Sprite) super.clone();
			clone.components = copyComponents();
			for (Component component : clone.components.values()) {
				if (component instanceof PropertiesComponent)
					clone.propertiesComponent = (PropertiesComponent) component;
				else if (component instanceof FrameStateMachineComponent)
					clone.frameStateMachineComponent = (FrameStateMachineComponent) component;
			}
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
	public void injectComponents(){
		ComponentInjector.inject(this);
	}

	/**
	 * This method will copy entirely the Sprite's component-map following rules:
	 * If a component implements Shareable or it doesn't implement Cloneable,
	 * the component should is shared in the new map.
	 * Otherwise, the component should be invoked clone(), hence it's expected having a
	 * different reference to the original component.
	 * @return the copied components.
	 */
	private Map<String, Component> copyComponents(){
		HashMap<String, Component> cloneComponents = new HashMap<>();
		for (String type : this.components.keySet())
		{
			Component component = this.components.get(type);
			if (isComponentSharedOnly(component))
				cloneComponents.put(type, component);
			else
				cloneComponents.put(type, ((CloneableComponent)component).clone());
		}
		return cloneComponents;
	}

	private boolean isComponentSharedOnly(Component component) {
		return !(component instanceof CloneableComponent) || component instanceof Shareable;
	}

	/**
	 * @return all the frames that should be rendered in the present state.
	 */
	public Collection<Frame> getRenderedFrames() {
		Set<Frame> frames = new LinkedHashSet<>();
		for (Renderable renderable : renderableComponents)
			frames.addAll(renderable.getRenderedFrames());
		return frames;
	}

	public Collection<Renderable> getRenderableComponents() {
		return renderableComponents;
	}


	/**
	 * @return all components not marked by Shareable interface
	 * @see Shareable
	 */
	public Set<Component> getNonshareableComponents(){
		return components.values().stream()
				.filter(c -> ! (c instanceof Shareable) )
				.collect(Collectors.toSet());
	}

	/**
	 * @return all components marked by Shareable interface
	 * @see Shareable
	 */
	public Set<Component> getShareableComponents(){
		return components.values().stream()
					.filter(c ->c instanceof Shareable)
					.collect(Collectors.toSet());
	}

	public boolean isCollidable() {
        return hasComponent(Component.COLLIDABLE);
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
		StringBuilder stringBuilder = new StringBuilder("----- Sprite -----");
		for (String componentName : components.keySet()) {
			stringBuilder.append("\n== Component: ").append(componentName);
			stringBuilder.append(" ==\n").append(components.get(componentName));
		}
		return stringBuilder.toString();
	}
}
