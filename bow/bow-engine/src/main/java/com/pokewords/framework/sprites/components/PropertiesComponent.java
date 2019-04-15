package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.utils.StringUtility;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class PropertiesComponent implements Component {
	private Rectangle body = new Rectangle(0, 0, 0, 0);
	private String type;
	private String state;
	private List<PositionListener> positionListeners = new ArrayList<PositionListener>();
	private List<StateListener> stateListeners = new ArrayList<StateListener>();

	public PropertiesComponent() {
	}

	@Override
	public PropertiesComponent clone() {
		try {
			PropertiesComponent clone = (PropertiesComponent) super.clone();
			clone.body = (Rectangle) this.body.clone();
			clone.positionListeners = new ArrayList<>();
			clone.stateListeners = new ArrayList<>();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public Rectangle2D getBody() {
		return body;
	}

	public void setBody(Rectangle body) {
		this.body = body;
		notifyPositionListeners();
	}

	public void setPosition(int x, int y) {
		this.body.setLocation(x, y);
		notifyPositionListeners();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		notifyStateListeners();
	}

	@Override
	public void onAppStateStart(AppStateWorld world) {
        if (StringUtility.anyNullOrEmpty(type, state))
            throw new RuntimeException("The type and state of a Sprite should be set before the app started..");
	}

	@Override
	public void onUpdate(double tpf) {

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

	public interface PositionListener{
		void onPositionUpdated(Point2D point);
	}

	public interface StateListener{
		void onStateUpdated(String state);
	}

	public void addStateListener(StateListener stateListener){
		stateListeners.add(stateListener);
	}

	public void removeStateListener(StateListener stateListener){
		stateListeners.remove(stateListener);
	}

	/**
	 * Trigger all stateListener's onStateUpdated() method
	 */
	protected void notifyStateListeners(){
		stateListeners.forEach(listener -> listener.onStateUpdated(state));
	}

	public void addPositionListener(PositionListener positionListener){
		positionListeners.add(positionListener);
	}

	public void removePositionListener(PositionListener positionListener){
		positionListeners.remove(positionListener);
	}

	/**
	 * Trigger all positionListener's onPositionUpdated() method
	 */
	protected void notifyPositionListeners(){
		positionListeners.forEach(listener -> listener.onPositionUpdated(point));
	}
}
