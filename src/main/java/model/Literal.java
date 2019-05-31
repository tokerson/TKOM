package model;

import program.MyRunTimeException;

public abstract class Literal<T,VT> extends Node implements Executable {

    protected boolean isTrue = false;

    public abstract VT getValue();

    public abstract Literal add(final Literal second) throws MyRunTimeException;

    public abstract T substract(final Literal second) throws MyRunTimeException;

    public abstract T multiply(final Literal second) throws MyRunTimeException;

    public abstract T divide(final Literal second) throws MyRunTimeException;

    public void setTrue(boolean isTrue){
        this.isTrue = isTrue;
    }

    public boolean isTrue(){
        return isTrue;
    }

    public T or(final Literal second){
        this.isTrue = this.isTrue || second.isTrue();
        return (T) this;
    }

    public T and(final Literal second){
        this.isTrue = this.isTrue && second.isTrue();
        return (T) this;
    }

    public T isEqual(final Literal second) throws MyRunTimeException {
        this.isTrue = this.isTrue == second.isTrue();
        return (T) this;
    }

    public T isNotEqual(final Literal second) throws MyRunTimeException {
        this.isTrue = this.isTrue != second.isTrue();
        return (T) this;
    }

    public abstract T isGreaterThan(final Literal second) throws MyRunTimeException;

    public abstract T isGreaterOrEqualThan(final Literal second) throws MyRunTimeException;

    public abstract T isLessThan(final Literal second) throws MyRunTimeException;

    public abstract T isLessOrEqualThan(final Literal second) throws MyRunTimeException;

    public abstract MyType getEvaluatedType();

}
