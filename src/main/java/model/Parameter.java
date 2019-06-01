package model;

import model.Token.TokenType;

public class Parameter{
    private MyType type;
    private String name;

    public Parameter(MyType type, String name) {
        this.type = type;
        this.name = name;
    }

    public boolean isArray() {
        return type.isArray();
    }

    public TokenType getType() {
        return type.getType();
    }

    public MyType getParameterType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return type +
                ": " +
                name;
    }

}
