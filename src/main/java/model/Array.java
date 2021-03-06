package model;

import model.Token.TokenType;
import program.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;

public class Array extends Node implements Executable {

    private TokenType elementsType;
    private List<Literal> elements = new ArrayList<Literal>();

    public Array(TokenType type) {
        this.elementsType = type;
    }

    public void addElement(Literal element) {
        elements.add(element);
    }

    public List<Literal> getElements() {
        return elements;
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

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        return this;
    }
}
