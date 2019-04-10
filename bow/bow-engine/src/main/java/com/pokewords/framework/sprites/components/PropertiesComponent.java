package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PropertiesComponent implements Component {
	private Point2D point;
	private String type;
	private String state;
	private List<PositionListener> positionListeners = new ArrayList<PositionListener>();
	private List<StateListener> stateListeners = new ArrayList<StateListener>();

	@Override
	public void onBoundToSprite(Sprite sprite) { }

	@Override
	public void onStart() { }

	@Override
	public void onUpdate() { }

	public Point2D getPoint() {
		return point;
	}

	public void setPoint(Point2D point) {
		this.point = point;
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
