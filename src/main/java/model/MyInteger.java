package model;

import model.Token.TokenType;

public class MyInteger extends Node {

    private int value;

    public MyInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.Integer;
    }
}
