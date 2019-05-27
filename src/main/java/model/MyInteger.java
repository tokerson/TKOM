package model;

import model.Token.TokenType;
import semcheck.MyRunTimeException;

public class MyInteger extends Literal<MyInteger, Integer> {

    private int value;

    public MyInteger(int value) {
        this.value = value;
    }

    public Integer getValue() {
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
    public MyInteger add(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyInteger(value + (int)second.getValue());
        } else if (second instanceof MyDouble){
            if(((MyDouble) second).getValue() % 1 == 0){
                return new MyInteger((int) (value + ((MyDouble) second).getValue()));
            } else throw new MyRunTimeException("Cannot add double to integer");
        } else throw new MyRunTimeException("Cannot add non-integer to integer");
    }

    @Override
    public MyInteger substract(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyInteger(value - (int)second.getValue());
        } else if (second instanceof MyDouble){
            if(((MyDouble) second).getValue() % 1 == 0){
                return new MyInteger((int) (value - ((MyDouble) second).getValue()));
            } else throw new MyRunTimeException("Cannot substract double from integer");
        } else throw new MyRunTimeException("Cannot substract non-integer to integer");
    }

    @Override
    public MyInteger multiply(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyInteger(value *((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            return new MyInteger((int) (value * ((MyDouble) second).getValue()));
        } else throw new MyRunTimeException("Cannot multiply integer by NaN");
    }

    @Override
    public MyInteger divide(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyInteger(value /((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            return new MyInteger((int) (value / ((MyDouble) second).getValue()));
        } else throw new MyRunTimeException("Cannot divide integer by NaN");    }

    @Override
    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public MyInteger isEqual(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            this.setTrue(this.value == ((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            if(((MyDouble) second).getValue() % 1 == 0){
                this.isTrue = this.value == ((MyDouble) second).getValue();
            } else {
                this.isTrue = false;
            }
        } else {
            throw new MyRunTimeException("Cannot compare int with NaN");
        }
        return this;
    }

    @Override
    public MyInteger isNotEqual(Literal second) throws MyRunTimeException {
        this.isTrue = !this.isEqual(second).isTrue();
        return this;
    }

    @Override
    public MyInteger isGreaterThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            this.isTrue = this.value > ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value > ((MyDouble) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare int with NaN");
        }
        return this;
    }

    @Override
    public MyInteger isGreaterOrEqualThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            this.isTrue = this.value >= ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value >= ((MyDouble) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare int with NaN");
        }
        return this;
    }

    @Override
    public MyInteger isLessThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            this.isTrue = this.value < ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value < ((MyDouble) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare int with NaN");
        }
        return this;
    }

    @Override
    public MyInteger isLessOrEqualThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            this.isTrue = this.value <= ((MyInteger) second).getValue();
        } else if(second instanceof MyDouble){
            this.isTrue = this.value <= ((MyDouble) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare int with NaN");
        }
        return this;
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        this.isTrue = value > 0;
        return this;
    }

    @Override
    public MyType getEvaluatedType() {
        return new MyType(false, TokenType.INT_TYPE);
    }

}
