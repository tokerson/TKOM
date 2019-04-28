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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(identifier);
        stringBuilder.append(" = ");
        stringBuilder.append(expression.toString());
        return stringBuilder.toString();
    }
}
