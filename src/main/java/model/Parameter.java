package model;

import model.Token.TokenType;

public class Parameter{
    private TokenType type;
    private String name;

    public Parameter(TokenType type, String name) {
        this.type = type;
        this.name = name;
    }

    public TokenType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
