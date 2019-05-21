package com.pokewords.framework.sprites.components;

import java.awt.*;
import java.util.Objects;

/**
 * @author johnny850807 (waterball)
 */
public class PropertiesComponent extends CloneableComponent {
	private double timePerFrame;
	private Rectangle area = new Rectangle(0, 0, 0, 0);
	private Rectangle body = new Rectangle(0, 0, 0, 0);
	private Point center = new Point();
	private Object type;

	public PropertiesComponent() {
	}

	public PropertiesComponent(Object type) {
		this.type = type;
	}

	@Override
	public void onUpdate(double timePerFrame) {
		this.timePerFrame = timePerFrame;
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
		this.body.setBounds(body);
	}

	public void move(int velocityX, int velocityY) {
		getArea().translate(velocityX, velocityY);
	}

	public void moveX(int velocityX) {
		getArea().translate( velocityX, 0);
	}

	public void moveY(int velocityY) {
		getArea().translate(0, velocityY);
	}


	public int getX(){
		return (int) getPosition().getX();
	}
	public int getY(){
		return (int) getPosition().getY();
	}
	public int getWidth(){
		return (int) getArea().getWidth();
	}
	public int getHeight(){
		return (int) getArea().getHeight();
	}

	public Point getPosition(){
		return area.getLocation();
	}

	public void setPosition(Point position) {
		this.area.setLocation(position);
	}

	public void setPosition(int x, int y) {
		this.area.setLocation(x, y);
	}

	public Rectangle getArea() {
		return area;
	}

	public void setArea(Rectangle area) {
		this.area.setBounds(area);
	}

	public void setArea(int x, int y, int w, int h) {
		this.area.setBounds(x, y, w, h);
	}

	public void setAreaSize(int w, int h) {
		area.setSize(w, h);
	}
	public void setAreaSize(Dimension area) {
		area.setSize(area);
	}

	public Dimension getAreaSize() {
		return getArea().getSize();
	}

	public Dimension getBodySize() {
		return getBody().getSize();
	}

	public boolean isType(Object type) {
		return getType() == type;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public void setCenter(int x, int y) {
		this.setCenter(new Point(x, y));
	}

	public void setCenter(Point center) {
		this.center.setLocation(center);
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
		return area.equals(that.area) &&
				body.equals(that.body) &&
				center.equals(that.center) &&
				type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, body, center, type);
	}

	@Override
	public String toString() {
		return "PropertiesComponent{" +
				"area=" + area +
				", body=" + body +
				", center=" + center +
				", type=" + type +
				'}';
	}
}
