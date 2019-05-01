package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.sprites.components.Shareable;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.mocks.MockComponent;
import com.pokewords.framework.sprites.components.mocks.MockFrame;
import com.pokewords.framework.sprites.components.mocks.MockRenderableComponent;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pokewords.framework.engine.utils.StubFactory.Sprites.SimpleSprite.createSimpleSprite;
import static com.pokewords.framework.engine.utils.StubFactory.Sprites.SpriteWithOnlyMockComponent.*;
import static org.junit.Assert.*;

public class SpriteTest extends AbstractTest {

    @Test
    public void testGetAllShareableComponents() {
        Sprite sprite = createSimpleSprite();
        Set<Component> components = sprite.getShareableComponents();
        for (Component component : components) {
            assertTrue(component instanceof Shareable);
        }
    }

    @Test
    public void testGetAllNonshareableComponents() {
        Sprite sprite = createSimpleSprite();
        Set<Component> components = sprite.getNonshareableComponents();
        for (Component component : components) {
            assertFalse(component instanceof Shareable);
        }
    }

    @Test
    public void testSpriteClone() {
        Sprite sprite = createSimpleSprite();
        Sprite clone = sprite.clone();
        testSpriteFieldsCloned(sprite, clone);
    }

    private void testSpriteFieldsCloned(Sprite spriteStub, Sprite clone) {
        assertNotSameButEquals(spriteStub, clone);
        assertNotSameButEquals(spriteStub.components, clone.components);
        assertNotSameButEquals(spriteStub.getPropertiesComponent(), clone.getPropertiesComponent());

        // all sprites of the same type must share the FSMC !
        //assertSame(spriteStub.getFrameStateMachineComponent(), clone.getFrameStateMachineComponent());

        testComponentsShareability(spriteStub.components, clone.components);
    }

    private void testComponentsShareability(Map<String, Component> stubComponents, Map<String, Component> clonedComponents) {
        for (String name : stubComponents.keySet()) {
            Component stubComponent = stubComponents.get(name);
            Component cloneComponent = clonedComponents.get(name);
            if (stubComponent instanceof Shareable)
                assertSame(stubComponent, cloneComponent);
            else
                assertNotSameButEquals(stubComponent, cloneComponent);
        }
    }

    @Test
    public void testGetRenderedFrames() {
        Sprite sprite = createSimpleSprite();
        MockRenderableComponent r1 = new MockRenderableComponent();
        MockFrame rf1 = new MockFrame("rf1");
        r1.addFrame(rf1);

        MockRenderableComponent r2 = new MockRenderableComponent();
        MockFrame rf2 = new MockFrame("rf2");
        r1.addFrame(rf2);

        MockRenderableComponent r3 = new MockRenderableComponent();
        MockFrame rf3 = new MockFrame("rf3");
        r1.addFrame(rf3);

        sprite.putComponent("r1", r1);
        sprite.putComponent("r2", r2);
        sprite.putComponent("r3", r3);

        Collection<Frame> currentFrames = sprite.getRenderedFrames();
        assertTrue(currentFrames.contains(StubFactory.FSCM.FRAME_A));  //the initial state of the stub should be contained
        assertTrue(currentFrames.contains(rf1));
        assertTrue(currentFrames.contains(rf2));
        assertTrue(currentFrames.contains(rf3));
    }

    @Test
    public void testSpriteEqualsAndHashCode() {
        //given two sprites with same type and body
        Sprite sp1 = new Sprite();
        Sprite sp2 = new Sprite();
        sp1.setType("type");
        sp1.setBody(50, 50, 50, 50);
        sp2.setType("type");
        sp2.setBody(50, 50, 50, 50);

        //then they should be equal, and hashcode should be the same
        assertEquals(sp1, sp2);
        assertEquals(sp1.hashCode(), sp2.hashCode());
    }

    @Test
    public void testGetComponentByName() {
        Sprite sprite = createSpriteWithOnlyMockComponents();

        // when get components by their names, they should exist
        assertTrue(sprite.getComponentByName(MOCK1).isPresent());
        assertTrue(sprite.getComponentByName(MOCK2).isPresent());
        assertTrue(sprite.getComponentByName(MOCK3).isPresent());
    }

    @Test
    public void testRemoveComponentByName() {
        Sprite sprite = createSpriteWithOnlyMockComponents();

        // when remove those mock components by their names
        sprite.removeComponentByName(MOCK1);
        sprite.removeComponentByName(MOCK2);
        sprite.removeComponentByName(MOCK3);

        // then those components should be removed successfully
        assertFalse(sprite.getComponentByName(MOCK1).isPresent());
        assertFalse(sprite.getComponentByName(MOCK2).isPresent());
        assertFalse(sprite.getComponentByName(MOCK3).isPresent());
    }

    @Test
    public void testSpriteDelegatesLifeCycleToComponents() {
        final int UPDATE_LOOP = 100;

        Sprite sprite = createSpriteWithOnlyMockComponents();

        //when the sprite got triggered app's life cycles
        sprite.onAppStateStart(new AppStateWorld());

        sprite.onAppStateEnter();

        for (int i = 0; i < UPDATE_LOOP; i++) {
            sprite.onUpdate(15);
        }

        sprite.onAppStateExit();
        sprite.onAppStateEnter();
        sprite.onAppStateExit();
        sprite.onAppStateDestroy();

        //then all its components should also be triggered the same times each life cycle
        assertAllMockComponentsHaveBeenTriggered(2, UPDATE_LOOP, 2,
                getSpriteMockComponents(sprite));
    }

    private Collection<MockComponent> getSpriteMockComponents(Sprite sprite) {
        return sprite.getComponents().stream()
                .filter(c -> c instanceof MockComponent)
                .map(c -> (MockComponent) c)
                .collect(Collectors.toSet());
    }

    private void assertAllMockComponentsHaveBeenTriggered(int enterCount, int updateCount,
                                                          int exitCount, Collection<MockComponent> mockComponents) {
        for (MockComponent mockComponent : mockComponents) {
            assertTrue(mockComponent.hasStarted());
            assertEquals(enterCount, mockComponent.getEnterCount());
            assertEquals(updateCount, mockComponent.getUpdateCount());
            assertEquals(exitCount, mockComponent.getExitCount());
            assertTrue(mockComponent.hasDestroyed());
        }
    }
}
