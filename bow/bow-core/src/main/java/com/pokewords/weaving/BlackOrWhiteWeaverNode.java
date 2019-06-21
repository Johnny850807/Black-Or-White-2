package com.pokewords.weaving;

import com.pokewords.components.CharacterComponent;
import com.pokewords.components.DefenseComponent;
import com.pokewords.components.actions.*;
import com.pokewords.constants.InjuryTypes;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.HpComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.GameEffect;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.sprites.parsing.Segment;

/**
 * @author johnny850807 (waterball)
 */
public class BlackOrWhiteWeaverNode implements SpriteWeaver.Node {
    @Override
    public void onWeaving(Script script, Sprite sprite, IocContainer iocContainer) {
        for (Segment segment : script.getSegments("capability"))
            parseCapabilitySegment(new CapabilitySegment(segment), sprite);

        for (Segment segment : script.getSegments("frame"))
            parseWithinFrame(segment, sprite);

    }

    private void parseCapabilitySegment(CapabilitySegment capabilitySegment, Sprite sprite) {
        sprite.getComponent(DefenseComponent.class).setDefense(capabilitySegment.getDefense());
        sprite.getComponent(HpComponent.class).setHp(capabilitySegment.getHp());
    }

    private void parseWithinFrame(Segment frameSegment, Sprite sprite) {
        EffectFrame effectFrame = sprite.getFrameStateMachineComponent().getFrame(frameSegment.getId());
        parseWithinBodyElement(effectFrame, frameSegment.getFirstElement("body"));
        for (Element actionElement : frameSegment.getElements()) {
            parseActionElement(effectFrame, actionElement);
        }
    }

    private void parseWithinBodyElement(EffectFrame effectFrame, Element bodyElement) {
        if (bodyElement.containsKey("injury")) {
            int injury = bodyElement.getInt("injury");
            InjuryTypes injuryType = bodyElement.containsKey("injuryType") ?
                    InjuryTypes.valueOf(bodyElement.getString("injuryType")) :
                    InjuryTypes.NORMAL;
            effectFrame.addEffect(parseBodyInjuryEffect(injury, injuryType));
        }
    }

    private GameEffect parseBodyInjuryEffect(int injury, InjuryTypes injuryType) {
        return (world, sprite) -> world.getSpritesCollidedWith(sprite).forEach(collidedSprite -> {
            collidedSprite.getComponent(CharacterComponent.class).injure(injuryType, injury);
        });
    }

    private void parseActionElement(EffectFrame effectFrame, Element actionElement) {
        effectFrame.addEffect((world, sprite) -> {
            ActionComponent action = getActionComponent(effectFrame.getId(), actionElement.getString("name"), sprite);
            action.action();
        });
    }

    private ActionComponent getActionComponent(int frameId, String actionName, Sprite sprite) {
        actionName = actionName.toLowerCase();
        switch (actionName)
        {
            case "shoot":
                return sprite.getComponent(ShootComponent.class);
            case "pick":
                return sprite.getComponent(PickComponent.class);
            case "pursue":
                return sprite.getComponent(PursueComponent.class);
            case "useitem":
                return sprite.getComponent(UseItemComponent.class);
                default:
                    throw new IllegalArgumentException(String.format(
                            "The action %s is not owned by the sprite of type %s, but it's used in the frame (%d).",
                            actionName, sprite.getType(), frameId));
        }
    }
}
