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
    public boolean isTrue() {
        return value > 0;
    }

    @Override
    public MyDouble isEqual(Literal second){
        if(second instanceof MyDouble){
            this.isTrue = this.value == ((MyDouble) second).getValue();
        } else if (second instanceof MyInteger){
            this.isTrue = this.value == (double)((MyInteger) second).getValue();
        }
        return this;
    }


    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        this.isTrue = value > 0;
        return this;
    }
}
