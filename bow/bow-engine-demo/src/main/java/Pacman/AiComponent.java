package Pacman;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CloneableComponent;

import java.util.Random;

public class AiComponent extends CloneableComponent {
    private final static Random random = new Random();
    private Sprite sprite;

    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        if ((int)timePerFrame % 2 == 0 && random.nextInt(100) > 95)
        {
            int negativeX = random.nextBoolean() ? 1 : -1;
            int negativeY = random.nextBoolean() ? 1 : -1;
            int dx = random.nextInt(30) * negativeX;
            int dy = random.nextInt(30) * negativeY;
            sprite.move(dx, dy);
        }
    }
}
