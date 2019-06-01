package com.pokewords.framework.sprites.parsing;


/**
 * @author nyngwang
 */
public abstract class Element implements Node {
    private Node parent;
    private String name;
    protected KeyValuePairs keyValuePairs;

    public Element(Node parent, String name, KeyValuePairs keyValuePairs) {
        this.parent = parent;
        this.name = name;
        this.keyValuePairs = keyValuePairs;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyValuePairs getKeyValuePairs() {
        return keyValuePairs;
    }

}
