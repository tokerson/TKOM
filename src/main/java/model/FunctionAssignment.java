package model;

public class FunctionAssignment extends Node {
    private String identifier;
    private Expression expression;

    public FunctionAssignment(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Type getType() {
        return Type.FunctionAssignment;
    }
}
