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
    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public MyInteger isEqual(Literal second){
        if(second instanceof MyInteger){
            this.setTrue(this.value == ((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            if(((MyDouble) second).getValue() % 1 == 0){
                this.isTrue = this.value == (int) ((MyDouble) second).getValue();
            } else {
                this.isTrue = false;
            }
        }
        return this;
    }

    @Override
    public MyInteger isNotEqual(Literal second){
        this.isTrue = !this.isEqual(second).isTrue();
        return this;
    }

    @Override
    public MyInteger isGreaterThan(Literal second) {
        if(second instanceof MyInteger){
            this.isTrue = this.value > ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value > ((MyDouble) second).getValue();
        }
        return this;
    }

    @Override
    public MyInteger isGreaterOrEqualThan(Literal second) {
        if(second instanceof MyInteger){
            this.isTrue = this.value >= ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value >= ((MyDouble) second).getValue();
        }
        return this;
    }

    @Override
    public MyInteger isLessThan(Literal second) {
        if(second instanceof MyInteger){
            this.isTrue = this.value < ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value < ((MyDouble) second).getValue();
        }
        return this;
    }

    @Override
    public MyInteger isLessOrEqualThan(Literal second) {
        if(second instanceof MyInteger){
            this.isTrue = this.value <= ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value <= ((MyDouble) second).getValue();
        }
        return this;
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        this.isTrue = value > 0;
        return this;
    }
}
