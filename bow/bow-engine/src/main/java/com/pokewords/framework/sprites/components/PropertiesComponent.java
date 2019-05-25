package com.pokewords.framework.sprites.components;

import java.awt.*;
import java.util.Objects;

/**
 * @author johnny850807 (waterball)
 */
public class PropertiesComponent extends CloneableComponent {
    private Rectangle area = new Rectangle(0, 0, 0, 0);
    private Point latestPosition = area.getLocation();
    private Rectangle body;
    private Point center;
    private Object type;

    public PropertiesComponent() { }

    public PropertiesComponent(Object type) {
        this.type = type;
    }

    @Override
    public void onUpdate(double timePerFrame) {}

    @Override
    public PropertiesComponent clone() {
        PropertiesComponent clone = (PropertiesComponent) super.clone();
        clone.area = (Rectangle) this.area.clone();
        clone.latestPosition = clone.area.getLocation();
        if (body != null)
            clone.body = (Rectangle) this.body.clone();
        if (center != null)
            clone.center = (Point) this.center.clone();
        return clone;
    }


    public Rectangle getBody() {
        if (body == null)
            body = area;
        return body;
    }

    public void setBody(int x, int y, int w, int h) {
        setBody(new Rectangle(x, y, w, h));
    }

    public void setBody(Rectangle body) {
        if (this.body == null)
            this.body = new Rectangle(body);
        else
            this.body.setBounds(body);
    }

    public void move(Point point) {
        move(point.x, point.y);
    }

    public void moveX(int velocityX) {
        move(velocityX, 0);
    }

    public void moveY(int velocityY) {
        move(0, velocityY);
    }

    public void move(int velocityX, int velocityY) {
        recordLatestPosition();
        getArea().translate(velocityX, velocityY);
    }

    public int getX() {
        return (int) getPosition().getX();
    }

    public int getY() {
        return (int) getPosition().getY();
    }

    public int getWidth() {
        return (int) getArea().getWidth();
    }

    public int getHeight() {
        return (int) getArea().getHeight();
    }

    public Point getPosition() {
        return area.getLocation();
    }

    public void setPosition(Point position) {
        setPosition(position.x, position.y);
    }

    public void setPosition(int x, int y) {
        recordLatestPosition();
        getArea().setLocation(x, y);
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        setArea(area.x, area.y,area.width,area.height);
    }

    public void setArea(int x, int y, int w, int h) {
        recordLatestPosition();
        getArea().setBounds(x, y, w, h);
    }

    public void setAreaSize(int w, int h) {
        getArea().setSize(w, h);
    }

    public void setAreaSize(Dimension area) {
        getArea().setSize(area);
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
        if (this.center == null)
            this.center = new Point(center);
        else
            this.center.setLocation(center);
    }

    public Point getCenter() {
        if (center == null)
        {
            int bodyX = (int) getBody().getX();
            int bodyY = (int) getBody().getY();
            center = new Point(bodyX + (int) getBody().getWidth() / 2, bodyY + (int) getBody().getHeight() / 2);
        }
        return center;
    }

    private void recordLatestPosition() {
        latestPosition.setLocation(area.getX(), area.getY());
    }

    public void resumeToLatestPosition() {
        area.setLocation(latestPosition.x, latestPosition.y);
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
        return getArea().equals(that.getArea()) &&
                getBody().equals(that.getBody()) &&
                getCenter().equals(that.getCenter()) &&
                getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArea(), getBody(), getCenter(), getType());
    }

    @Override
    public String toString() {
        return "PropertiesComponent{" +
                "area=" + getArea() +
                ", body=" + getBody() +
                ", center=" + getCenter() +
                ", type=" + getType() +
                '}';
    }

}
