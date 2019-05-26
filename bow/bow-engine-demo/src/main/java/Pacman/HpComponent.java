package Pacman;

import com.pokewords.framework.sprites.components.CloneableComponent;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class HpComponent extends CloneableComponent implements Renderable {
    private final static int hpFrameHeight = 6;
    private final static int hpFrameGapHeightFromSprite = 12;
    private RectangleFrame lostHpFrame;
    private RectangleFrame remainingHpFrame;
    private Collection<Frame> renderedFrames;
    private int maxHp = 5;
    private int remainingHp;

    public HpComponent() {
        this(5);
    }

    public HpComponent(int maxHp) {
        this.remainingHp = this.maxHp = maxHp;
        lostHpFrame = new RectangleFrame(3, Color.red).flags(RectangleFrame.CANVAS_FLAG_FILLED);
        remainingHpFrame = new RectangleFrame(3, Color.green).flags(RectangleFrame.CANVAS_FLAG_FILLED);;
        renderedFrames = new ArrayList<>(Arrays.asList(lostHpFrame, remainingHpFrame));
    }

    @Override
    public void onUpdate(double timePerFrame) {
        rearrangeHpFrames();
    }

    public void lostHp(int lostHp) {
        setHp(getHp() - lostHp);
    }

    public int getHp() {
        return remainingHp;
    }

    public void setHp(int remainingHp) {
        this.remainingHp = remainingHp <= 0 ? 0 : remainingHp;
        if (this.remainingHp <= 0)
            getAttachedWorld().removeSprite(getOwnerSprite());
        else
            rearrangeHpFrames();
    }

    private void rearrangeHpFrames() {
        int remainingHpWidth = (int) ((getOwnerSprite().getWidth() * remainingHp) / ((double) maxHp));
        int lostHpWidth = getOwnerSprite().getWidth() - remainingHpWidth;
        remainingHpFrame.setSize(new Dimension(remainingHpWidth, hpFrameHeight));
        lostHpFrame.setSize(new Dimension(lostHpWidth, hpFrameHeight));
        repositionHpFrames();
    }

    private void repositionHpFrames() {
        remainingHpFrame.setPosition(new Point(getOwnerSprite().getX(),
                getOwnerSprite().getY() - hpFrameGapHeightFromSprite - remainingHpFrame.getHeight()));
        lostHpFrame.setPosition(new Point(getOwnerSprite().getX() + remainingHpFrame.getWidth(),
                getOwnerSprite().getY() - hpFrameGapHeightFromSprite - lostHpFrame.getHeight()));
    }

    @Override
    public HpComponent clone() {
        HpComponent clone = (HpComponent) super.clone();
        clone.lostHpFrame = lostHpFrame.clone();
        clone.remainingHpFrame = remainingHpFrame.clone();
        clone.renderedFrames = new ArrayList<>(Arrays.asList(clone.lostHpFrame, clone.remainingHpFrame));
        return clone;
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return renderedFrames;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return renderedFrames;
    }
}
