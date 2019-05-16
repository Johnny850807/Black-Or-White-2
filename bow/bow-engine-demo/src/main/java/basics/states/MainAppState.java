package basics.states;


import basics.namespace.SpriteType;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.CollisionHandler;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

import java.awt.event.KeyEvent;
import java.util.Random;

public class MainAppState extends AppState {

    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        world.addCollisionHandler(new HeroCollidedWithBulletHandler());
        world.addCollisionHandler(new HeroCollidedWithMonster());
    }

    private static class HeroCollidedWithBulletHandler extends CollisionHandler {
        public HeroCollidedWithBulletHandler() {
            super(SpriteType.HERO, SpriteType.BULLET);
        }

        @Override
        public void onCollision(Sprite hero, Sprite bullet) {

        }
    }

    private static class HeroCollidedWithMonster extends CollisionHandler {
        public HeroCollidedWithMonster() {
            super(SpriteType.HERO, SpriteType.MONSTER);
        }

        @Override
        public void onCollision(Sprite hero, Sprite monster) {

        }
    }


    @Override
    public void onAppStateEntering() {
        AppStateWorld world = getAppStateWorld();
        world.spawn(createSprite(SpriteType.HERO));

        for (int i = 0; i < 8; i++)
            world.spawn(createMonsterAtRandomPosition());

    }

    private Sprite createMonsterAtRandomPosition() {
        Random random = new Random();
        Sprite monster = createSprite(SpriteType.MONSTER);
        monster.setPosition(random.nextInt(getWindowSize().x), random.nextInt(getWindowSize().y));
        return monster;
    }

    @Override
    public void onAppStateExiting() {
        getAppStateWorld().clearSprites();
    }

    @Override
    protected void onAppStateDestroying() {

    }


    @Override
    public void onAppStateUpdating(int timePerFrame) {
        if (getInputs().getButtonBeingHeld(KeyEvent.VK_W))
            moveUp();
        if (getInputs().getButtonBeingHeld(KeyEvent.VK_S))
            moveDown();
        if (getInputs().getButtonBeingHeld(KeyEvent.VK_A))
            moveLeft();
        if (getInputs().getButtonBeingHeld(KeyEvent.VK_D))
            moveRight();
    }

    private void moveUp(){

    }
    private void moveLeft(){

    }
    private void moveDown(){

    }
    private void moveRight(){

    }
}
