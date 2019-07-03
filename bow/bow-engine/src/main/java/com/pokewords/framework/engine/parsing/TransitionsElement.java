package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.commons.utils.EnumUtility;
import com.pokewords.framework.engine.weaver.GameEngineWeaverNode;
import com.pokewords.framework.sprites.parsing.Element;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author johnny850807 (waterball)
 */
public class TransitionsElement {
    private Class<?> enumType;
    private Map<Object, Integer> transitionMap = new HashMap<>();  // <event type, to frame's event>

    public TransitionsElement(Function<String, Object> enumProvider, Element element) {
        Collection<String> keys = element.getKeys();

        for (String event : keys) {
            if (!event.equals("classType")) {
                Object eventEnum = enumProvider.apply(event);
                transitionMap.put(eventEnum, element.getInt(event));
            }
        }
    }


    public Class<?> getEnumType() {
        return enumType;
    }

    public Map<Object, Integer> getTransitionMap() {
        return transitionMap;
    }


}
