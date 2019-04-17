package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.GalleryAttributeNameDoesNotExistException;
import com.pokewords.framework.views.Gallery;

import java.util.HashMap;
import java.util.Map;

/**
 * TL;DR: I modified this class because it makes parsing easier.
 *
 * Since the script is defined by client, it's client's job to provide
 * correct attribute tag name to access the correct value. (In the original
 * design it's our job to know the mapping, which makes parsing complicate.
 *
 * e.g. "col" -> int columnNumber
 * Then we have to know:
 *     1. "col" maps to 'int'
 *     2. "col" should be set by setColumnNumber()
 *
 * @author nyngwang
 */
public class GallerySegment {

    private Map<String, Integer> intMap;
    private Map<String, String> stringMap;

    public GallerySegment() {
        this.intMap = new HashMap<>();
        this.stringMap = new HashMap<>();
    }

    public Gallery toGallery(){
        return new Gallery(intMap.get("w"), intMap.get("h"), intMap.get("row"), intMap.get("col"));
    }

    public void addPair(String key, int value) {
        intMap.put(key, value);
    }

    public void addPair(String key, String value) {
        stringMap.put(key, value);
    }

    public int getInt(String name) {
        if (!intMap.containsKey(name)) {
            throw new GalleryAttributeNameDoesNotExistException(
                    "Gallery attribute name does not exist!");
        }
        return intMap.get(name);
    }

    public String getString(String name) {
        if (!stringMap.containsKey(name)) {
            throw new GalleryAttributeNameDoesNotExistException(
                    "Gallery attribute name does not exist!");
        }
        return stringMap.get(name);
    }
}
