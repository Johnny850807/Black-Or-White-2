package com.pokewords.framework.sprites;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.engine.utils.StubFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.ComponentMap;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Shareable;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.mocks.*;
import org.junit.Test;

import java.util.Collection;
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

        assertSame(spriteStub.getFrameStateMachineComponent(), clone.getFrameStateMachineComponent());
        assertSame(spriteStub.getCollidableComponent(), clone.getCollidableComponent());

        testComponentsShareability(spriteStub.components, clone.components);
    }

    private void testComponentsShareability(ComponentMap stubComponents, ComponentMap clonedComponents) {
        for (Class<? extends Component> type : stubComponents.keySet()) {
            Component stubComponent = stubComponents.get(type);
            Component cloneComponent = clonedComponents.get(type);
            if (stubComponent instanceof Shareable)
                assertSame(stubComponent, cloneComponent);
            else
                assertNotSameButEquals(stubComponent, cloneComponent);
        }
    }

    @Test
    public void testGetRenderedFrames() {
        Sprite sprite = createSimpleSprite();
        MockRenderableComponent1 r1 = new MockRenderableComponent1();
        MockFrame rf1 = new MockFrame("rf1");
        r1.addFrame(rf1);

        MockRenderableComponent2 r2 = new MockRenderableComponent2();
        MockFrame rf2 = new MockFrame("rf2");
        r1.addFrame(rf2);

        MockRenderableComponent3 r3 = new MockRenderableComponent3();
        MockFrame rf3 = new MockFrame("rf3");
        r1.addFrame(rf3);

        sprite.addComponent(r1);
        sprite.addComponent(r2);
        sprite.addComponent(r3);

        Collection<? extends Frame> currentFrames = sprite.getRenderedFrames();
        assertTrue(currentFrames.contains(StubFactory.FrameStateMachineComponents.FRAME_A));  //the initial state of the stub should be contained
        assertTrue(currentFrames.contains(rf1));
        assertTrue(currentFrames.contains(rf2));
        assertTrue(currentFrames.contains(rf3));
    }

    @Test
    public void testSpriteEqualsAndHashCode() {
        //given two sprites with same type and body
        Sprite sp1 = new Sprite("type");
        sp1.setBody(50, 50, 50, 50);
        Sprite sp2 = new Sprite("type");
        sp2.setBody(50, 50, 50, 50);

        //then they should be equal, and hashcode should be the same
        assertEquals(sp1, sp2);
        assertEquals(sp1.hashCode(), sp2.hashCode());
    }

    @Test
    public void testGetComponentByName() {
        Sprite sprite = createSpriteWithOnlyMockComponents();

        // when get components by their names, they should exist
        assertTrue(sprite.hasComponent(MockComponent1.class));
        assertTrue(sprite.hasComponent(MockComponent2.class));
        assertTrue(sprite.hasComponent(MockComponent3.class));
    }

    @Test
    public void testRemoveComponentByName() {
        Sprite sprite = createSpriteWithOnlyMockComponents();

        // when remove those mock components by their names
        sprite.removeComponent(MockComponent1.class);
        sprite.removeComponent(MockComponent2.class);
        sprite.removeComponent(MockComponent3.class);

        // then those components should be removed successfully
        assertFalse(sprite.hasComponent(MockComponent1.class));
        assertFalse(sprite.hasComponent(MockComponent2.class));
        assertFalse(sprite.hasComponent(MockComponent3.class));
    }

    @Test
    public void testSpriteDelegatesLifeCycleToComponents() {
        final int UPDATE_LOOP = 100;

        Sprite sprite = createSpriteWithOnlyMockComponents();

        //when the sprite got triggered app's life cycles
        sprite.onAppStateCreate(new AppStateWorld());

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
