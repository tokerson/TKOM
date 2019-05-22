package model;

import model.Token.TokenType;
import semcheck.MyRunTimeException;

public class MyInteger extends Literal<MyInteger> {

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

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public MyInteger add(MyInteger second) {
        return new MyInteger(value + second.getValue());
    }

    @Override
    public MyInteger substract(MyInteger second) {
        return new MyInteger(value - second.getValue());
    }

    @Override
    public MyInteger multiply(MyInteger second) {
        return new MyInteger(value * second.getValue());
    }

    @Override
    public MyInteger divide(MyInteger second) {
        return new MyInteger(value / second.getValue());
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        return this;
    }
}
