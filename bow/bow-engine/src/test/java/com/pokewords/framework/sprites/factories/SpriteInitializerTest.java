package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.engine.exceptions.SpriteDeclarationException;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.mocks.MockComponentImp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author johnny850807 (waterball)
 */
public class SpriteInitializerTest extends AbstractTest {
    MockPrototypeFactory mockPrototypeFactory;
    MockDefaultSpriteBuilder mockSpriteBuilder;
    final String TYPE = "type";
    final MockComponentImp mockComponent = new MockComponentImp();

    SpriteInitializer spriteInitializer;

    @Before
    public void setup() {
        spriteInitializer = new SpriteInitializer(new ReleaseIocContainer(){
            @Override
            public PrototypeFactory prototypeFactory() {
                return mockPrototypeFactory = new MockPrototypeFactory();
            }

            @Override
            public SpriteBuilder spriteBuilder() {
                return mockSpriteBuilder = new MockDefaultSpriteBuilder(this);
            }
        });
    }

    @Test
    public void testShouldThrowExceptionIfForgotCommit() {
        spriteInitializer.declare("TargetPair")
                        .area(10, 10, 10, 10);

        try {
            spriteInitializer.declare("Another TargetPair");
            fail();
        } catch (GameEngineException ignored) { }
    }

    @Test
    public void testNonLazyModeShouldCreateSpriteAndPrototypeDuringDeclaration() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.NON_LAZY);

        declareSprite();

        assertSpritebuilderAndPrototypeFactoryHaveBeenInvoked();
    }

    @Test
    public void testLazyModeShouldNotCreateSpriteAndPrototypeDuringDeclaration() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.LAZY);

        declareSprite();

        assertSpritebuilderAndPrototypeFactoryHaveNotBeenInvoked();

        spriteInitializer.createSprite(TYPE);

        assertSpritebuilderAndPrototypeFactoryHaveBeenInvoked();
    }



    @Test
    public void testCustomModeShouldOnlyCreateSpriteAndPrototypeDuringInvokingInit() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.CUSTOM);

        declareSprite();

        assertSpritebuilderAndPrototypeFactoryHaveNotBeenInvoked();

        spriteInitializer.initSprite(TYPE);

        assertSpritebuilderAndPrototypeFactoryHaveBeenInvoked();

        spriteInitializer.createSprite(TYPE);
    }


    @Test
    public void testCustomModeShouldHelpCreateSpriteAndPrototypeIfCreationInvokedWithoutCustomInit() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.CUSTOM);

        declareSprite();

        assertSpritebuilderAndPrototypeFactoryHaveNotBeenInvoked();

        spriteInitializer.createSprite(TYPE);

        assertSpritebuilderAndPrototypeFactoryHaveBeenInvoked();
    }

    @Test
    public void testCustomStrictModeShouldOnlyCreateSpriteAndPrototypeDuringInvokingInit() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.CUSTOM_STRICT);

        declareSprite();

        assertSpritebuilderAndPrototypeFactoryHaveNotBeenInvoked();

        spriteInitializer.initSprite(TYPE);

        assertSpritebuilderAndPrototypeFactoryHaveBeenInvoked();

        spriteInitializer.createSprite(TYPE);
    }

    @Test
    public void testCustomStrictModeShouldThrowExceptionIfCreationInvokedWithoutCustomInit() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.CUSTOM_STRICT);

        declareSprite();

        assertSpritebuilderAndPrototypeFactoryHaveNotBeenInvoked();

        assertCreateSpriteThrowException();
    }

    private void assertCreateSpriteThrowException() {
        try {
            spriteInitializer.createSprite(TYPE);
            fail();
        } catch (SpriteDeclarationException err) {

        }
    }

    private void assertSpritebuilderAndPrototypeFactoryHaveNotBeenInvoked() {
        assertNull(mockSpriteBuilder.getBuiltSprite());
        assertNull(mockPrototypeFactory.getPrototypeSprite());
        assertNull(mockPrototypeFactory.getClonedType());
    }

    private void assertSpritebuilderAndPrototypeFactoryHaveBeenInvoked() {
        Sprite builtSprite = mockSpriteBuilder.getBuiltSprite();
        Sprite prototypeSprite = mockPrototypeFactory.getPrototypeSprite();

        assertNotNull(builtSprite);
        assertNotNull(prototypeSprite);
        assertSame(builtSprite, prototypeSprite);

        assertSame(mockComponent, builtSprite.getComponent(mockComponent.getClass()));
        assertSame(mockComponent, prototypeSprite.getComponent(mockComponent.getClass()));
        assertSame(TYPE, mockPrototypeFactory.getClonedType());
    }

    @Test
    public void testShouldThrowExceptionIfInvokeInitUnderLazyMode() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.LAZY);

        declareSprite();

        assertInitSpriteThrowException();
    }

    @Test
    public void testShouldThrowExceptionIfInvokeInitUnderNonLazyMode() {
        spriteInitializer.setInitializationMode(SpriteInitializer.InitializationMode.NON_LAZY);

        declareSprite();

        assertInitSpriteThrowException();
    }

    private void assertInitSpriteThrowException() {
        try {
            spriteInitializer.initSprite(TYPE);
            fail();
        } catch (SpriteDeclarationException err) {

        }
    }

    private void declareSprite() {
        spriteInitializer.declare(TYPE)
                .with(mockComponent)
                .commit();
    }

}