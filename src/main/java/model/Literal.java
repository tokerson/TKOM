package model;

public abstract class Literal<T> extends Node implements Executable {

    public abstract T add(final T second);

    public abstract T substract(final T second);

    public abstract T multiply(final T second);

    public abstract T divide(final T second);

}
