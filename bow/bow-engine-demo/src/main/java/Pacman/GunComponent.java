package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.marks.Shareable;

public class GunComponent extends Component implements Shareable {
    private long loopTime = 0;
    private long latestShootLoopTime = 0;

    @Override
    public void onUpdate(double timePerFrame) {
        loopTime = (loopTime + 1) % Long.MAX_VALUE;

    }

    public void shootIfAvailable(Direction direction) {
        if ((loopTime - latestShootLoopTime) >= 7)
            shoot(direction);
    }

    private void shoot(Direction direction) {
        latestShootLoopTime = loopTime;
        Sprite bullet = getGameEngineFacade().createSprite(Types.BULLET);
        bullet.setPosition(getOwnerSprite().getCenter());
        BulletComponent bulletComponent = bullet.getComponent(BulletComponent.class);
        bulletComponent.setDirection(direction);
        bulletComponent.setBulletOwnerType(getOwnerSprite().getType());
        getAttachedWorld().spawn(bullet);
    }
}
