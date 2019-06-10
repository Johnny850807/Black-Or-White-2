package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import java.awt.*;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author johnny850807 (waterball)
 */
public class PropertiesComponent extends CloneableComponent {
    private LatestStates latestStates = new LatestStates();
    private Rectangle area = new Rectangle();
    private Rectangle body = new Rectangle();
    private Point center = new Point();
    private boolean hasBody = false;
    private boolean hasCenter = false;
    private CompositeType compositeType;

    private HashSet<SpritePositionChangedListener> positionChangedListeners = new HashSet<>();

    public interface SpritePositionChangedListener {
        void onSpritePositionChanged(Sprite sprite);
    }

    public PropertiesComponent(Object ...types) {
        this.compositeType = new CompositeType(types);
        latestStates.recordStates();
    }


    @Override
    public void onAppStateEnter() {
        Objects.requireNonNull(compositeType);
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
        clone.positionChangedListeners = new HashSet<>();
        if (hasBody)
            clone.body = (Rectangle) this.body.clone();
        if (hasCenter)
            clone.center = (Point) this.center.clone();
        clone.latestStates = clone.new LatestStates();
        clone.latestStates.recordStates();
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
        latestStates.recordStates();
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
        latestStates.recordStates();
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
        latestStates.recordStates();
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
        latestStates.recordStates();
        boolean positionChanged = getArea().x != x || getArea().y != y;
        getArea().setBounds(x, y, w, h);

        if (positionChanged)
            notifyPositionChangedListeners();
    }

    public void setAreaSize(Dimension area) {
        setAreaSize(area.width, area.height);
    }

    public void setAreaSize(int w, int h) {
        latestStates.recordStates();
        getArea().setSize(w, h);
    }

    public Dimension getAreaSize() {
        return getArea().getSize();
    }

    public Dimension getBodySize() {
        return getBody().getSize();
    }

    public boolean isType(Object type) {
        return getType().isType(type);
    }

    public boolean anyType(Object ...types) {
        for (Object t : types) {
            if (isType(t))
                return true;
        }
        return false;
    }

    public void setType(Object ...types) {
        setType(new CompositeType(types));
    }

    public void setType(CompositeType type) {
        this.compositeType = type;
    }

    public CompositeType getType() {
        return compositeType;
    }

    public Object getConcreteType() {
        return compositeType.getConcreteType();
    }

    public void setCenter(Point center) {
        setCenter(center.x, center.y);
    }

    public void setCenter(int x, int y) {
        latestStates.recordStates();
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

    public void resumeToLatestPosition() {
        area.setLocation(latestStates.area.getLocation());
    }

    public LatestStates getLatestStates() {
        return latestStates;
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
                ", compositeType=" + getType() +
                '}';
    }


    public class LatestStates {
        public Rectangle area = new Rectangle();
        public Rectangle body = new Rectangle();
        public Point center = new Point();

        protected void recordStates() {
            this.area.setBounds(getArea());
            this.body.setBounds(getBody());
            this.center.setLocation(getCenter());
        }

        public void resume() {
            setArea(area);
            setBody(body);
            setCenter(center);
        }
    }
}
