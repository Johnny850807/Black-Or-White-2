package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.sprites.MockSprite;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.mocks.MockEffectFrame;
import com.pokewords.framework.sprites.components.mocks.MockRenderableComponent;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.pokewords.framework.engine.utils.StubFactory.Sprites.SimpleSprite.createSimpleSprite;
import static org.junit.Assert.*;

/**
 * @author Shawn
 */
public class AppStateWorldTest {
    private AppStateWorld appStateWorld = new AppStateWorld();
    private final String HERO_TYPE = "Hero";
    private final String MELEE_MINION_TYPE = "Melee Minion";
    private final String BULLET_TYPE = "Bullet";
    private final String AREA = "Just area";
    private final int timePerFrame = 150;

    @Test
    public void testAppStateWorldLifecycleHasBeenDelegatedToSprite() {
        final int updateLoopCount = 100;

        List<MockSprite> sprites = creatSprites(20);
        for (Sprite sprite : sprites)
            appStateWorld.spawn(sprite);

        appStateWorld.onAppStateCreate();
        appStateWorld.onAppStateEnter();

        for (int i = 0; i < updateLoopCount; i++) {
            appStateWorld.onUpdate(15);
        }

        appStateWorld.onAppStateExit();
        appStateWorld.onAppStateDestroy();

        for (MockSprite sprite : sprites) {
            assertEquals(1, sprite.onAppStateStartCount);
            assertEquals(1, sprite.onAppStateEnterCount);
            assertEquals(updateLoopCount, sprite.onUpdateCount);
            assertEquals(1, sprite.onAppStateExitCount);
            assertEquals(1, sprite.onAppStateDestroyCount);
        }

    }


    private List<MockSprite> creatSprites(int count) {
        List<MockSprite> sprites = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sprites.add(new MockSprite("mock"));
        }
        return sprites;
    }

    @Test
    public void testSpawnIfExist(){
        List<MockSprite> sprites = creatSprites(50);
        for (Sprite sprite : sprites) {
            int spawnId = appStateWorld.spawn(sprite);
            assertEquals(spawnId, appStateWorld.getId(sprite));
            assertEquals(sprite, appStateWorld.getSprite(spawnId));
        }
    }

    @Test
    public void testSpriteIfExist(){
        List<MockSprite> sprites = creatSprites(50);
        for (Sprite sprite : sprites) {
            int spawnId = appStateWorld.spawn(sprite);
            assertTrue(appStateWorld.contains(spawnId));
            assertTrue(appStateWorld.contains(sprite));
        }
    }

    @Test
    public void testSpawnDelay() throws InterruptedException {
        Sprite mockSprite = createSingleSprite(HERO_TYPE,150, 150, 50, 100);
        appStateWorld.spawnDelay(mockSprite, 2, TimeUnit.SECONDS, (Integer spawnId)-> {
            assertEquals(mockSprite, appStateWorld.getSprite(spawnId));
        });
        Thread.sleep(3000);
    }


    @Test
    public void testClearSprites(){
        List<MockSprite> sprites = creatSprites(50);
        for (Sprite sprite : sprites) {
            appStateWorld.spawn(sprite);
        }
        appStateWorld.clearSprites();
        assertTrue(appStateWorld.getSprites().isEmpty());
    }


    @Test
    public void testTwoSpriteIfCollision(){
        Sprite spriteA = createSingleSprite(HERO_TYPE,150, 150, 50, 100);
        appStateWorld.spawn(spriteA);

        Sprite mockSpriteB = createSingleSprite(HERO_TYPE,150, 150, 50, 100);
        appStateWorld.spawn(mockSpriteB);

        Collection<Sprite> collisionSpriteList2 =  appStateWorld.getSpritesCollidedWith(spriteA);
        assertEquals(Collections.singletonList(mockSpriteB), collisionSpriteList2);



        Sprite mockSpriteC = createSingleSprite(HERO_TYPE,0, 0, 50, 100);
        appStateWorld.spawn(mockSpriteC);

        Sprite mockSpriteD = createSingleSprite(HERO_TYPE,49, 99, 50, 100);
        appStateWorld.spawn(mockSpriteD);

        Collection<Sprite> collisionSpriteList = appStateWorld.getSpritesCollidedWith(mockSpriteC);
        assertEquals(Collections.singletonList(mockSpriteD), collisionSpriteList);
    }


    @Test
    public void testMultipleSpriteIfCollision() {
        Sprite mockSpriteHero = createSingleSprite(HERO_TYPE,0, 0, 50, 100);
        appStateWorld.spawn(mockSpriteHero);

        List<MockSprite> sprites = creatSprites(6);
        for (Sprite sprite : sprites) {
            sprite.setBody(10, 10, 50, 100);
            appStateWorld.spawn(sprite);
        }

        Collection<Sprite> collisionSpriteList =  appStateWorld.getSpritesCollidedWith(mockSpriteHero);
        assertEquals(sprites, collisionSpriteList);
    }


    @Test
    public void testSingleSpriteWithInAreaWhenSpriteIsTotallyContainedInArea(){
        Sprite spriteA = createSingleSprite(AREA,150, 150, 50, 100);
        MockRenderableComponent mockRenderComponent = new MockRenderableComponent();
        MockEffectFrame mockFrame = new MockEffectFrame("mockFrame");
        mockRenderComponent.addFrame(mockFrame);
        spriteA.setBody(150, 150, 50, 100);
        spriteA.addComponent(mockRenderComponent);


        Sprite mockSprite = createSingleSprite(AREA,0, 0, 100, 200);
        appStateWorld.spawn(mockSprite);


        Dimension dimension = new Dimension(101, 201);
        Collection<Sprite> sprites  = appStateWorld.getSpritesWithinArea(spriteA, dimension);
        assertEquals(Collections.singletonList(mockSprite), sprites);
    }


    @Test
    public void testSpritesWithInAreaWhenSpriteIsTotallyContainedInArea(){
        List<MockSprite> sprites = creatSprites(5);
        int spriteX = 0;
        for(Sprite sprite : sprites) {
            sprite.setBody(spriteX, 0, 50, 100);
            appStateWorld.spawn(sprite);
            spriteX += 60;
        }
        int areaX = 0;
        for(Sprite sprite : sprites){
            Rectangle area = new Rectangle(areaX, 0, 55, 105);
            Collection<Sprite> areaSprites  = appStateWorld.getSpritesWithinArea(area);
            assertEquals(Collections.singletonList(sprite), areaSprites);
            areaX += 60;
        }
    }



    @Test
    public void testSingleSpritesWithInAreaAssertFalseWhenSpriteIsCompletelyOutOfArea(){
        Sprite sprite = createSingleSprite(HERO_TYPE, 50, 100, 50, 100);
        appStateWorld.spawn(sprite);
        Rectangle area = new Rectangle(0, 0, 50, 100);
        Collection<Sprite> sprites  = appStateWorld.getSpritesWithinArea(area);
        assertFalse(!sprites.isEmpty());
    }


    @Test
    public void testSpritesWithInAreaAssertFalseWhenSpriteIsTotallyOutOfArea(){
        List<MockSprite> sprites = creatSprites(5);
        int spriteX = 0;
        for(Sprite sprite : sprites) {
            sprite.setBody(spriteX, 0, 50, 100);
            appStateWorld.spawn(sprite);
            spriteX += 60;
        }
        int areaX = 0;
        for(int i = 0 ; i < sprites.size(); i++){
            Rectangle area = new Rectangle(areaX, 101, 55, 105);
            Collection<Sprite> areaSprites  = appStateWorld.getSpritesWithinArea(area);
            assertFalse(!areaSprites.isEmpty());
            areaX += 60;
        }
    }

    @Test
    public void testSingleSpritesWithInAreaWhenSpriteOccupiesPartOfArea(){
        Sprite sprite = createSingleSprite(HERO_TYPE, 0, 0, 50, 100);
        appStateWorld.spawn(sprite);

        Collection<Sprite> sprites  = appStateWorld.getSpritesWithinArea(sprite, 25, 50);
        assertEquals(Collections.singletonList(sprite), sprites);

        Collection<Sprite> sprites2  = appStateWorld.getSpritesWithinArea(sprite, new Dimension(25, 50));
        assertEquals(Collections.singletonList(sprite), sprites2);
    }

    @Test
    public void testSpriteWithInAreaWhenSpriteOccupiesPartOfArea(){
        List<MockSprite> sprites = creatSprites(6);
        int spriteY = 0;
        int mid = sprites.size() / 2;
        for(int i = 0; i < mid; i++) {
            Sprite sprite = sprites.get(i);
            sprite.setBody(0, spriteY, 50, 100);
            appStateWorld.spawn(sprite);
            spriteY += 60;
        }

        spriteY = 0;
        for(int i = mid; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            sprite.setBody(60, spriteY, 50, 100);
            appStateWorld.spawn(sprite);
            spriteY += 60;
        }

        Collection<Sprite> areaSprites  = appStateWorld.getSpritesWithinArea(25, 50, 60, 110);
        assertEquals(sprites, areaSprites);
    }

    private Sprite createSingleSprite(String type, int x, int y, int width, int height){
        MockSprite sprite = new MockSprite(type);
        sprite.setBody(x, y, width, height);
        return sprite;
    }

    @Test
    public void testHeroWillBeRepelledIfHeroCollisionMeleeMinion(){
        appStateWorld.addCollisionHandler(new CollisionHandler(HERO_TYPE, MELEE_MINION_TYPE) {
            @Override
            public void onCollision(Sprite s1, Sprite s2) {
                if(s1.getType().equals(MELEE_MINION_TYPE)) {
                    int heroX = s2.getX();
                    int heroY = s2.getY();
                    s1.setPosition(heroX + 51, heroY);

                    Collection<Sprite> areaSprites  = appStateWorld.getSpritesWithinArea(heroX, heroY, s1.getWidth(), s1.getHeight());
                    assertEquals(Collections.singletonList(s2), areaSprites);
                }
            }
        });
        Sprite meleeMinionSprite = createSingleSprite(MELEE_MINION_TYPE, 0,0,50,100);
        appStateWorld.spawn(meleeMinionSprite);
        Sprite heroSprite = createSingleSprite(HERO_TYPE, 0,0,50,100);
        appStateWorld.spawn(heroSprite);
        appStateWorld.onUpdate(timePerFrame);
    }

    @Test
    public void testMeleeMinionWillDisappearWhenBulletHitsMeleeMinion(){
        appStateWorld.addCollisionHandler(new CollisionHandler(BULLET_TYPE, MELEE_MINION_TYPE) {
            @Override
            public void onCollision(Sprite s1, Sprite s2) {
                if(s1.getType().equals(MELEE_MINION_TYPE)) {
                    appStateWorld.removeSprite(s1);
                    assertTrue(!appStateWorld.contains(s1));
                }
            }
        });

        Sprite meleeMinionSprite = createSingleSprite(MELEE_MINION_TYPE, 0,0,50,100);
        appStateWorld.spawn(meleeMinionSprite);

        Sprite bulletSprite = createSingleSprite(BULLET_TYPE, 0,0,50,100);
        appStateWorld.spawn(bulletSprite);
        appStateWorld.onUpdate(timePerFrame);
    }

    @Test
    public void testRenderedLayersIfUpdateWhenAppStateWorldTriggerOnUpdate(){
        Sprite sprite = createSimpleSprite();
        FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();

        EffectFrame frameA = new MockEffectFrame(0, "A");
        frameStateMachineComponent.addFrame(frameA);
        EffectFrame frameB = new MockEffectFrame(1, "B");
        frameStateMachineComponent.addFrame(frameB);

        frameStateMachineComponent.setCurrentFrame(frameA);
        frameStateMachineComponent.addTransition(frameA, Events.UPDATE, frameB);
        sprite.addComponent(frameStateMachineComponent);
        appStateWorld.spawn(sprite);

        assertEquals(frameA, getFrameByRenderedLayer(frameA.getLayerIndex(), frameA.getId()));
        appStateWorld.onUpdate(timePerFrame);

        assertEquals(frameB, getFrameByRenderedLayer(frameB.getLayerIndex(), frameB.getId()));
    }

    private Frame getFrameByRenderedLayer(int index, int id) {
        return appStateWorld.getRenderedLayers().getLayers().get(index).get(id);
    }

}
