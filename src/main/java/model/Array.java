package model;

import model.Token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Array extends Node {

    private TokenType type;
    private List elements = new ArrayList();

    public Array(TokenType type) {
        this.type = type;
    }

    public void addElement(Node element) {
        elements.add(element);
    }

    public List getElements() {
        return elements;
    }

    @Override
    public Type getType() {
        return Type.Array;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if(elements.size() > 0){
            stringBuilder.append(elements.get(0));
        }
        for(int i = 1; i < elements.size();++i){
            stringBuilder.append(",");
            stringBuilder.append(elements.get(i));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
