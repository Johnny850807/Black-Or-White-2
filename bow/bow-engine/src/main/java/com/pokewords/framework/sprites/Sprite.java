package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.MandatoryComponentRequiredException;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author johnny850807, nyngwang
 */
public class Sprite implements Cloneable, AppStateLifeCycleListener {
	protected AppStateWorld world;
	protected Map<String, Component> components = new HashMap<>();
	private Collection<Renderable> renderableComponents = new HashSet<>();
	private FrameStateMachineComponent frameStateMachineComponent;
	private PropertiesComponent propertiesComponent;

	/**
	 * default constructor, empty FrameStateMachineComponent and default PropertiesComponent
	 */
	protected Sprite() {
		this(new FrameStateMachineComponent(), new PropertiesComponent());
	}

	public Sprite(final FrameStateMachineComponent frameStateMachineComponent) {
		this(frameStateMachineComponent, new PropertiesComponent());
	}

	/**
	 * The constructor of Sprite.
	 * @param frameStateMachineComponent The mandatory component to define the view of the Sprite.
	 * @param propertiesComponent The mandatory component to define the properties of the Sprite.
	 */
	public Sprite(final FrameStateMachineComponent frameStateMachineComponent,
				  final PropertiesComponent propertiesComponent) {
		putComponent(Component.FRAME_STATE_MACHINE, frameStateMachineComponent);
		putComponent(Component.PROPERTIES, propertiesComponent);
	}

	/**
	 * @return all components
	 */
	public Collection<Component> getComponents() {
		return components.values();
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

	public void setFrameStateMachineComponent(@NotNull FrameStateMachineComponent frameStateMachineComponent) {
		this.frameStateMachineComponent = frameStateMachineComponent;
		putComponent(Component.FRAME_STATE_MACHINE, frameStateMachineComponent);
	}

	public void setPropertiesComponent(@NotNull PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
		putComponent(Component.PROPERTIES, propertiesComponent);
	}

	/**
	 * Get the component by name.
	 * @param name the name of the component to get.
	 * @return the component to get if the name exist, null-object otherwise.
	 */
	public Optional<Component> getComponentByName(String name) {
		return Optional.ofNullable(components.get(name));
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
			this.propertiesComponent = (PropertiesComponent) component;
		else if (component instanceof FrameStateMachineComponent)
			this.frameStateMachineComponent = (FrameStateMachineComponent) component;
		components.put(name, component);
	}

	/**
	 * Remove the component by name.
	 * @param name the name of the component to be removed.
	 * @return the removed component if the name exist, null-object otherwise.
	 */
	public Optional<Component> removeComponentByName(String name) {
		Component component = components.get(name);
		if (component instanceof FrameStateMachineComponent)
			throw new MandatoryComponentRequiredException("FrameStateMachineComponent cannot be removed.");
		else if (component instanceof PropertiesComponent)
			throw new MandatoryComponentRequiredException("Properties Component cannot be removed.");
		return Optional.ofNullable(components.remove(name));
	}

	@Override
	public void onAppStateStart(AppStateWorld world){
		this.world = world;
		for (Component component : components.values())
			component.onAppStateStart(world);
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

    public Point2D getPosition() {
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
        return getComponentByName(Component.COLLIDABLE).isPresent();
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
