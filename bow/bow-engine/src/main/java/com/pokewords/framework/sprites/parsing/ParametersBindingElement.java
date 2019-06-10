package com.pokewords.framework.sprites.parsing;


/**
 * @author nyngwang
 */
public class ParametersBindingElement extends AngularElement {
    public ParametersBindingElement() {
        super("parameters");
    }

    @Override
    public void parse(Context context) {
        super.parse(context);
        context.bind(getKeyValuePairs().getMap());
    }
}
