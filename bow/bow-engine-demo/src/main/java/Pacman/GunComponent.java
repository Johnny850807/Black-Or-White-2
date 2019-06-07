package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CloneableComponent;

public class GunComponent extends CloneableComponent {
    private int shootInterval;
    private int bulletSpeed;
    private boolean shootAvailable = true;

    public GunComponent(int shootInterval, int bulletSpeed) {
        this.shootInterval = shootInterval;
        this.bulletSpeed = bulletSpeed;
    }

    public void shootIfAvailable(Direction direction) {
        if (shootAvailable)
            shoot(direction);
    }

    private void shoot(Direction direction) {
        Sprite bullet = getGameEngineFacade().createSprite(Types.BULLET);
        bullet.setPosition(getOwnerSprite().getCenter());
        BulletComponent bulletComponent = bullet.getComponent(BulletComponent.class);
        bulletComponent.setDirection(direction);
        bulletComponent.setBulletOwnerType(getOwnerSprite().getType());
        bulletComponent.setSpeed(bulletSpeed);
        getAttachedWorld().spawn(bullet);

        shootAvailable = false;
        getGameEngineFacade().addLoopCountdownHook(shootInterval, this::enableShootAvailable);
    }

    private void enableShootAvailable() {
        shootAvailable = true;
    }
}
