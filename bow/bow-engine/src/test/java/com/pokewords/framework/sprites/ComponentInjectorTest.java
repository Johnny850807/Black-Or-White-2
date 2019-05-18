package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.mocks.MockComponent1;
import com.pokewords.framework.sprites.components.mocks.MockComponent2;
import com.pokewords.framework.sprites.components.mocks.MockComponent3;
import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * @author johnny850807 (waterball)
 */
public class ComponentInjectorTest {

    @Test
    public void test() {
        MockComponent1 mockComponent1 = new MockComponent1();
        MockComponent2 mockComponent2 = new MockComponent2();
        MockComponent3 mockComponent3 = new MockComponent3();
        Sprite sprite = new Sprite("Type");

        sprite.addComponent(mockComponent1);
        sprite.addComponent(mockComponent2);
        sprite.addComponent(mockComponent3);

        sprite.injectComponents();
        assertSame(sprite, mockComponent1.getSprite());
        assertSame(sprite, mockComponent2.getSprite());
        assertSame(sprite, mockComponent3.getSprite());
    }

}