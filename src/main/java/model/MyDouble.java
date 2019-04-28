package model;

public class MyDouble extends Node {

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
}
