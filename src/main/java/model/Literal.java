package model;

public abstract class Literal<T> extends Node implements Executable {

    protected boolean isTrue = false;

    public abstract T add(final T second);

    public abstract T substract(final T second);

    public abstract T multiply(final T second);

    public abstract T divide(final T second);

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

    public T isEqual(final Literal second){
        this.isTrue = this.isTrue == second.isTrue();
        return (T) this;
    }

    public T isNotEqual(final Literal second){
        this.isTrue = this.isTrue != second.isTrue();
        return (T) this;
    }

    public abstract T isGreaterThan(final Literal second);

    public abstract T isGreaterOrEqualThan(final Literal second);

    public abstract T isLessThan(final Literal second);

    public abstract T isLessOrEqualThan(final Literal second);

    public abstract MyType getEvaluatedType();

}
