package com.pokewords.framework.sprites.components;

import com.pokewords.framework.commons.Logger;
import com.pokewords.framework.engine.utils.StringUtility;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author johnny850807 (waterball)
 */
public class PropertiesComponent extends CloneableComponent {
	private Rectangle body = new Rectangle(0, 0, 0, 0);
	private Point center = new Point();
	private Object type;

	public PropertiesComponent() {
	}

	public PropertiesComponent(Object type) {
		this.type = type;
	}

	@Override
	public PropertiesComponent clone() {
		PropertiesComponent clone = (PropertiesComponent) super.clone();
		clone.body = (Rectangle) this.body.clone();
		clone.center = (Point) this.center.clone();
		return clone;
	}

	public Rectangle getBody() {
		return body;
	}

	public void setBody(int x, int y, int w, int h){
		this.body.setBounds(x, y, w, h);
	}

	public void setBody(Rectangle body) {
		this.body = body;
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


	public Point getPosition(){
		return body.getLocation();
	}

	public void setPosition(Point position) {
		this.body.setLocation(position);
	}

	public void setPosition(int x, int y) {
		this.body.setLocation(x, y);
	}

	public Object getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCenter(int x, int y) {
		this.setCenter(new Point(x, y));
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Point getCenter() {
		return center;
	}

	@Override
	public void onAppStateCreate() {
		Objects.requireNonNull(type);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PropertiesComponent that = (PropertiesComponent) o;
		return body.equals(that.body) &&
				center.equals(that.center) &&
				type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, center, type);
	}

	@Override
	public String toString() {
		return "PropertiesComponent{" +
				"body=" + body +
				", center=" + center +
				", type='" + type + '\'' +
				'}';
	}
}
