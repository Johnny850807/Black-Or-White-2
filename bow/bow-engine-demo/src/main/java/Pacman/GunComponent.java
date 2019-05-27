package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CloneableComponent;

public class GunComponent extends CloneableComponent {
    private long loopTime = 0;
    private long latestShootLoopTime = 0;
    private int shootInterval;
    private int bulletSpeed;


    public GunComponent(int shootInterval, int bulletSpeed) {
        this.shootInterval = shootInterval;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        loopTime = (loopTime + 1) % Long.MAX_VALUE;
    }

    public void shootIfAvailable(Direction direction) {
        if ((loopTime - latestShootLoopTime) >= shootInterval)
            shoot(direction);
    }

    private void shoot(Direction direction) {
        latestShootLoopTime = loopTime;
        Sprite bullet = getGameEngineFacade().createSprite(Types.BULLET);
        bullet.setPosition(getOwnerSprite().getCenter());
        BulletComponent bulletComponent = bullet.getComponent(BulletComponent.class);
        bulletComponent.setDirection(direction);
        bulletComponent.setBulletOwnerType(getOwnerSprite().getType());
        bulletComponent.setSpeed(bulletSpeed);
        getAttachedWorld().spawn(bullet);
    }
}
