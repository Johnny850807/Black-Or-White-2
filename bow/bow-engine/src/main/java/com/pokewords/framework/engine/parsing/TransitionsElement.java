package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.commons.utils.EnumUtility;
import com.pokewords.framework.sprites.parsing.Element;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author johnny850807 (waterball)
 */
public class TransitionsElement {
    private Class<?> enumType;
    private Map<Object, Integer> transitionMap = new HashMap<>();  // <event type, to frame's event>

    public TransitionsElement(Element element) {
        parseAndValidateEnumType(element);

        Collection<String> keys = element.getKeyValuePairs().getKeys();

        for (String event : keys) {
            transitionMap.put(EnumUtility.evalEnum(enumType, event), element.getKeyValuePairs().getInt(event));
        }
    }

    private void parseAndValidateEnumType(Element element) {
        try {
            this.enumType = Class.forName(element.getKeyValuePairs().getString("classType"));
            if (!enumType.isEnum())
                throw new IllegalArgumentException(
                        String.format("The classType attribute within Transitions element should be of Enum type. " +
                                "(received: %s)", enumType));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Class<?> getEnumType() {
        return enumType;
    }

    public Map<Object, Integer> getTransitionMap() {
        return transitionMap;
    }


}
