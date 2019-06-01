package model;

import model.Token.TokenType;
import program.MyRunTimeException;

public class MyDouble extends Literal<MyDouble, Double> {

    private double value;

    public MyDouble(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Literal add(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyDouble(value +((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            return new MyDouble((value + ((MyDouble) second).getValue()));
        } else if (second instanceof MyString) {
            return new MyString(value + (String) second.getValue());
        } else throw new MyRunTimeException("Cannot add NaN to double.");
    }

    @Override
    public MyDouble substract(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyDouble(value -((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            return new MyDouble((value - ((MyDouble) second).getValue()));
        } else throw new MyRunTimeException("Cannot substract NaN from double.");
    }

    @Override
    public MyDouble multiply(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyDouble(value *((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            return new MyDouble((value * ((MyDouble) second).getValue()));
        } else throw new MyRunTimeException("Cannot multiply double by NaN.");
    }

    @Override
    public MyDouble divide(Literal second) throws MyRunTimeException {
        if(second instanceof MyInteger){
            return new MyDouble(value /((MyInteger) second).getValue());
        } else if (second instanceof MyDouble){
            return new MyDouble((value / ((MyDouble) second).getValue()));
        } else throw new MyRunTimeException("Cannot divide double by NaN.");
    }

    @Override
    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public MyDouble isEqual(Literal second) throws MyRunTimeException {
        if(second instanceof MyDouble){
            this.isTrue = this.value == ((MyDouble) second).getValue();
        } else if (second instanceof MyInteger){
            this.isTrue = this.value == (double)((MyInteger) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare double with NaN");
        }
        return this;
    }

    @Override
    public MyDouble isNotEqual(Literal second) throws MyRunTimeException {
        this.isTrue = !this.isEqual(second).isTrue();
        return this;
    }

    @Override
    public MyDouble isGreaterThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyDouble){
            this.isTrue = this.value > ((MyDouble) second).getValue();
        } else if(second instanceof MyInteger){
            this.isTrue = this.value > (double)((MyInteger) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare double with NaN");
        }
        return this;
    }

    @Override
    public MyDouble isGreaterOrEqualThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyDouble){
            this.isTrue = this.value >= ((MyDouble) second).getValue();
        } else if(second instanceof MyInteger){
            this.isTrue = this.value >= (double)((MyInteger) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare double with NaN");
        }
        return this;
    }

    @Override
    public MyDouble isLessThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyDouble){
            this.isTrue = this.value < ((MyDouble) second).getValue();
        } else if(second instanceof MyInteger){
            this.isTrue = this.value < (double)((MyInteger) second).getValue();
        }  else {
            throw new MyRunTimeException("Cannot compare double with NaN");
        }
        return this;
    }

    @Override
    public MyDouble isLessOrEqualThan(Literal second) throws MyRunTimeException {
        if(second instanceof MyDouble){
            this.isTrue = this.value <= ((MyDouble) second).getValue();
        } else if(second instanceof MyInteger){
            this.isTrue = this.value <= (double)((MyInteger) second).getValue();
        } else {
            throw new MyRunTimeException("Cannot compare double with NaN");
        }
        return this;
    }

    @Override
    public MyType getEvaluatedType() {
        return new MyType(false, TokenType.DOUBLE_TYPE);
    }


    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        this.isTrue = value > 0;
        return this;
    }

}
