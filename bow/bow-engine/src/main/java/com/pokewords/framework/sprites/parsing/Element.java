package com.pokewords.framework.sprites.parsing;


/**
 * @author nyngwang
 */
public abstract class Element implements Node {
    private Node parent;
    protected String name;
    protected KeyValuePairs keyValuePairs;

    public Element(String name, Node parent, KeyValuePairs keyValuePairs) {
        this.name = name;
        this.parent = parent;
        this.keyValuePairs = keyValuePairs;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
