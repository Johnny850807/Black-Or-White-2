package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
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
    public void testAddAndCloneAndRemovePrototype(){
        final String NAME = "Sprite";
        // given a simple sprite added as a prototype
        Sprite sprite = createSimpleSprite();
        prototypeFactory.addPrototype(NAME, sprite);

        // then clone it from the factory will work
        assertNotSameButEquals(sprite, prototypeFactory.cloneSprite(NAME));


        // given that sprite removed from the factory
        try{
            prototypeFactory.removePrototype(NAME);

            // then clone it should throw exception (not found)
            prototypeFactory.cloneSprite(NAME);
            fail();
        } catch (GameEngineException err) {

        }
    }



}