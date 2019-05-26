package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import java.awt.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.List;

/**
 * @author johnny850807 (waterball)
 */
public class PropertiesComponent extends CloneableComponent {
    private Rectangle area = new Rectangle();
    private Point latestPosition = new Point();
    private Rectangle body = new Rectangle();
    private Point center = new Point();
    private boolean hasBody = false;
    private boolean hasCenter = false;
    private Object type;

    private HashSet<SpritePositionChangedListener> positionChangedListeners = new HashSet<>();

    public interface SpritePositionChangedListener {
        void onSpritePositionChanged(Sprite sprite);
    }

    public PropertiesComponent(Object type) {
        this.type = Objects.requireNonNull(type);
    }


    @Override
    public void onAppStateEnter() {
        Objects.requireNonNull(type);
    }

    public void addPositionListener(SpritePositionChangedListener positionChangedListener) {
        positionChangedListeners.add(positionChangedListener);
    }

    public void removePositionChangedListener(SpritePositionChangedListener positionChangedListener) {
        positionChangedListeners.remove(positionChangedListener);
    }


    @Override
    @SuppressWarnings("unchecked")
    public PropertiesComponent clone() {
        PropertiesComponent clone = (PropertiesComponent) super.clone();
        clone.area = (Rectangle) this.area.clone();
        clone.latestPosition = this.area.getLocation();
        clone.positionChangedListeners = new HashSet<>();
        if (hasBody)
            clone.body = (Rectangle) this.body.clone();
        if (hasCenter)
            clone.center = (Point) this.center.clone();
        return clone;
    }


    public Rectangle getBody() {
        return hasBody ? new Rectangle(area.x + body.x, area.y + body.y, body.width, body.height ) : area;
    }

    public void setBody(Rectangle body) {
        Objects.requireNonNull(body);
        setBody(body.x, body.y, body.width, body.height);
    }

    public void setBody(int x, int y, int w, int h) {
        this.body.setBounds(x, y, w, h);
        hasBody = true;
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
        notifyPositionChangedListeners();
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
        notifyPositionChangedListeners();
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        setArea(area.x, area.y,area.width,area.height);
    }

    public void setArea(int x, int y, int w, int h) {
        recordLatestPosition();
        boolean positionChanged = getArea().x != x || getArea().y != y;
        getArea().setBounds(x, y, w, h);

        if (positionChanged)
            notifyPositionChangedListeners();
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

    public void setCenter(Point center) {
        setCenter(center.x, center.y);
    }

    public void setCenter(int x, int y) {
        center.setLocation(x, y);
        hasCenter = true;
    }

    public Point getCenter() {
        if (!hasCenter)
        {
            int bodyX = (int) getBody().getX();
            int bodyY = (int) getBody().getY();
            center = new Point(bodyX + getBody().width / 2, bodyY + getBody().height / 2);
        }
        return center;
    }

    private void recordLatestPosition() {
        latestPosition.setLocation(area.getX(), area.getY());
    }

    public void resumeToLatestPosition() {
        area.setLocation(latestPosition.x, latestPosition.y);
    }

    public void notifyPositionChangedListeners() {
        positionChangedListeners.forEach(p -> p.onSpritePositionChanged(getOwnerSprite()));
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
