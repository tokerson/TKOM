package model;

public class Condition extends Expression {
    private boolean isNegated = false;

    @Override
    public Type getType() {
        return Type.Condition;
    }
}
