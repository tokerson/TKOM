package model;

import model.Token.TokenType;

public class Parameter{
    private boolean array;
    private TokenType type;
    private String name;

    public Parameter(TokenType type, String name, boolean array) {
        this.type = type;
        this.name = name;
        this.array = array;
    }

    public boolean isArray() {
        return array;
    }

    public TokenType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
