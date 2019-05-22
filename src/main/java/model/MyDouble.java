package model;

import semcheck.MyRunTimeException;

public class MyDouble extends Literal<MyDouble> {

    private double value;

    public MyDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.Double;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public MyDouble add(MyDouble second) {
        return new MyDouble(value + second.getValue());
    }

    @Override
    public MyDouble substract(MyDouble second) {
        return new MyDouble(value - second.getValue());
    }

    @Override
    public MyDouble multiply(MyDouble second) {
        return new MyDouble(value * second.getValue());
    }

    @Override
    public MyDouble divide(MyDouble second) {
        return new MyDouble(value / second.getValue());
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        return this;
    }
}
