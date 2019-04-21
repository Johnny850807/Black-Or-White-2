package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.MandatoryComponentIsRequiredException;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author johnny850807, nyngwang
 */
public class Sprite implements Cloneable, AppStateLifeCycleListener {
	private FrameStateMachineComponent frameStateMachineComponent;
	private PropertiesComponent propertiesComponent;
	private Map<String, Component> components = new HashMap<>();

	public Sprite() {
	}

	/**
	 * The constructor of Sprite.
	 * @param frameStateMachineComponent The mandatory component to define the view of the Sprite.
	 * @param propertiesComponent The mandatory component to define the properties of the Sprite.
	 */
	public Sprite(final FrameStateMachineComponent frameStateMachineComponent,
				  final PropertiesComponent propertiesComponent) {
		this.frameStateMachineComponent = frameStateMachineComponent;
		this.propertiesComponent = propertiesComponent;
		components.put(Component.FRAME_STATE_MACHINE, frameStateMachineComponent);
		components.put(Component.PROPERTIES, propertiesComponent);
	}

	/**
	 * Return the entire component map.
	 * @return the component map.
	 */
	public Map<String, Component> getComponents() {
		return components;
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

	public void setFrameStateMachineComponent(FrameStateMachineComponent frameStateMachineComponent) {
		this.frameStateMachineComponent = frameStateMachineComponent;
		putComponent(Component.FRAME_STATE_MACHINE, frameStateMachineComponent);
	}

	public void setPropertiesComponent(PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
		putComponent(Component.PROPERTIES, propertiesComponent);
	}

	/**
	 * Get the component by name.
	 * @param name the name of the component to get.
	 * @return the component to get if the name exist, null-object otherwise.
	 */
	public Optional<Component> getComponentByName(String name) {
		return Optional.of(components.get(name));
	}

	/**
	 * Put new component with name. If the component you put is
	 * PropertiesComponent or FrameStateMachineComponent then this method will detect it.
	 * @param name the name of the component to be added.
	 * @param component the component to be added.
	 */
	public void putComponent(String name, Component component) {
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
			throw new MandatoryComponentIsRequiredException("FrameStateMachineComponent cannot be removed.");
		else if (component instanceof PropertiesComponent)
			throw new MandatoryComponentIsRequiredException("Properties Component cannot be removed.");
		return Optional.of(components.remove(name));
	}

	@Override
	public void onAppStateStart(AppStateWorld world){
	}

	@Override
	public void onUpdate(double tpf) {
        for (Component component : components.values())
            component.onUpdate(tpf);
	}

	@Override
	public void onAppStateEnter() {

	}

    @Override
    public void onAppStateExit() {

    }

    @Override
	public void onAppStateDestroy() {

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

	public void setBody(int x, int y, int w, int h){
		getPropertiesComponent().setBody(x, y, w, h);
	}
	public void setBody(Rectangle body){
		getPropertiesComponent().setBody(body);
	}

	public Rectangle getBody(){
		return getPropertiesComponent().getBody();
	}

	public void setPosition(Point position){
		getPropertiesComponent().setPosition(position);
	}

	public void setPosition(int x, int y){
		getPropertiesComponent().setPosition(x, y);
	}

	public Point2D getPosition(){
		return getPropertiesComponent().getPosition();
	}

	public int getX(){
		return (int) getPosition().getX();
	}

	public int getY(){
		return (int) getPosition().getY();
	}
	public int getW(){
		return (int) getBody().getWidth();
	}
	public int getH(){
		return (int) getBody().getHeight();
	}


	public void setType(String type){
		getPropertiesComponent().setType(type);
	}

	public String getType(){
		return getPropertiesComponent().getType();
	}

	public void setState(String state){
		getPropertiesComponent().setState(state);
	}

	public String getState(){
		return getPropertiesComponent().getState();

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
	 * Make the components injected
	 * @see ComponentInjector#inject(Sprite)
	 */
	public void injectComponents(){
		ComponentInjector.inject(this);
	}

	private Map<String, Component> copyComponents(){
		HashMap<String, Component> cloneComponents = new HashMap<>();
		for (String type : this.components.keySet())
		{
			Component component = this.components.get(type);
			if (component instanceof Shareable)
				cloneComponents.put(type, component);
			else
				cloneComponents.put(type, component.clone());
		}
		return cloneComponents;
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
}
