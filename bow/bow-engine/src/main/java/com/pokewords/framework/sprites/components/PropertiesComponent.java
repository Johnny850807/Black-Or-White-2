package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Logger;
import com.pokewords.framework.engine.utils.StringUtility;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertiesComponent extends Component {
	Logger logger = Logger.of(PropertiesComponent.class);
	private Rectangle body = new Rectangle(0, 0, 0, 0);
	private String type;
	private String state;
	private List<PositionListener> positionListeners = new ArrayList<PositionListener>();
	private List<StateListener> stateListeners = new ArrayList<StateListener>();

	public PropertiesComponent() {

	}

	@Override
	public PropertiesComponent clone() {
		PropertiesComponent clone = (PropertiesComponent) super.clone();
		clone.body = (Rectangle) this.body.clone();
		clone.positionListeners = new ArrayList<>();
		clone.stateListeners = new ArrayList<>();
		return clone;
	}

	public Rectangle getBody() {
		return body;
	}

	public void setBody(int x, int y, int w, int h){
		this.body.setBounds(x, y, w, h);
		notifyPositionListeners();
	}

	public void setBody(Rectangle body) {
		this.body = body;
		notifyPositionListeners();
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

	public Point2D getPosition(){
		return body.getLocation();
	}

	public void setPosition(Point position) {
		this.body.setLocation(position);
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

	public List<PositionListener> getPositionListeners() {
		return positionListeners;
	}

	public List<StateListener> getStateListeners() {
		return stateListeners;
	}

	/**
	 * Trigger all positionListener's onPositionUpdated() method
	 */
	protected void notifyPositionListeners(){
		positionListeners.forEach(listener -> listener.onPositionUpdated(body.getLocation()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PropertiesComponent that = (PropertiesComponent) o;
		return body.equals(that.body) &&
				type.equals(that.type) &&
				state.equals(that.state);
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, type, state);
	}
}
