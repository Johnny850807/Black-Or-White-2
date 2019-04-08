package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import javax.swing.plaf.nimbus.State;
import javax.swing.text.Position;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PropertiesComponent implements Component {
	private Point2D point;
	private String type;
	private String state;
	private List<PositionListener> positionListeners = new ArrayList<PositionListener>();
	private List<StateListener> stateListeners = new ArrayList<StateListener>();

	public void addStateListener(StateListener stateListener){
		this.stateListeners.add(stateListener);
	}

	public void removeStateListener(StateListener stateListener){
		this.stateListeners.remove(stateListener);
	}

	public void addPositionListener(PositionListener positionListener){
		this.positionListeners.add(positionListener);
	}

	public void removePositionListener(PositionListener positionListener){
		this.positionListeners.remove(positionListener);
	}

	@Override
	public void onBoundToSprite(Sprite sprite) {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onUpdate() {

	}

	public Point2D getPoint() {
		return point;
	}

	public void setPoint(Point2D point) {
		this.point = point;
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
	}

	public interface PositionListener{
		void onPositionUpdated(Point2D point);
	}
	public interface StateListener{
		void onStateUpdated(String state);
	}
}
