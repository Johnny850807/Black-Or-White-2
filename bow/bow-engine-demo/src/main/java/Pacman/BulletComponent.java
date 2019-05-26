package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CloneableComponent;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.views.windows.GameWindowDefinition;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;

public class BulletComponent extends CloneableComponent implements Renderable {
    private Direction direction;
    private RectangleFrame bulletFrame;
    private Collection<Frame> renderedFrame;

    public BulletComponent() {
        bulletFrame = new RectangleFrame(2, Color.yellow).flags(RectangleFrame.CANVAS_FLAG_FILLED);
        renderedFrame = Collections.singletonList(bulletFrame);
    }

    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        super.onComponentAttachedSprite(sprite);
        bulletFrame.boundToSprite(sprite);
    }

    @Override
    public void onComponentAttachedWorld(AppStateWorld appStateWorld) {
        super.onComponentAttachedWorld(appStateWorld);
        getGameEngineFacade().playSound(Types.SHOOT);
    }

    @Override
    public void onUpdate(double timePerFrame) {
        getOwnerSprite().move(direction.move(20));
        removeSelfIfOutOfScreen();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public BulletComponent clone() {
        BulletComponent clone = (BulletComponent) super.clone();
        clone.bulletFrame = bulletFrame.clone();
        clone.renderedFrame = Collections.singletonList(clone.bulletFrame);
        return clone;
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return renderedFrame;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return renderedFrame;
    }
}
