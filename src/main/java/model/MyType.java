package model;

import model.Token.TokenType;

public class MyType {
    private boolean array;
    private TokenType type;

    public MyType(boolean array, TokenType type) {
        this.array = array;
        this.type = type;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public TokenType getType() {
        return type;
    }
}
