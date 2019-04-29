package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.engine.utils.StubFactory;
import org.junit.Before;
import org.junit.Test;

import static com.pokewords.framework.engine.utils.StubFactory.Sprites.SimpleSprite.createSimpleSprite;
import static org.junit.Assert.*;

public class PrototypeFactoryTest extends AbstractTest {
    private PrototypeFactory prototypeFactory;

    @Before
    public void setup(){
         prototypeFactory = release.prototypeFactory();
    }

    @Test(expected = GameEngineException.class)
    public void testCloneNonexistentPrototypeShouldThrowException(){
        prototypeFactory.cloneSprite("NONE");
    }

    @Test
    public void testCloneExistentPrototype(){
        final String NAME = "Sprite";
        Sprite sprite = createSimpleSprite();
        prototypeFactory.addPrototype(NAME, sprite);
        assertNotSameButEquals(sprite, prototypeFactory.cloneSprite(NAME));
    }

    @Test
    public void testAddAndRemovePrototype(){
        final String NAME = "Sprite";
        Sprite sprite = createSimpleSprite();
        prototypeFactory.addPrototype(NAME, sprite);
        prototypeFactory.cloneSprite(NAME);

        try{
            prototypeFactory.removePrototype(NAME);
            prototypeFactory.cloneSprite(NAME);
            fail();
        }catch (GameEngineException err) { }
    }



}