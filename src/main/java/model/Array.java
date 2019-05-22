package model;

import model.Token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Array extends Node {

    private TokenType elementsType;
    private List<Node> elements = new ArrayList<Node>();

    public Array(TokenType type) {
        this.elementsType = type;
    }

    public void addElement(Node element) {
        elements.add(element);
    }

    public List<Node> getElements() {
        return elements;
    }

    @Override
    public Type getType() {
        return Type.Array;
    }

    public TokenType getElementsType(){
        return elementsType;
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
