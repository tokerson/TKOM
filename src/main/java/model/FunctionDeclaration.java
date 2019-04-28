package model;

public class FunctionDeclaration extends Node {
    String identifier;

    public FunctionDeclaration(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Type getType() {
        return Type.FunctionDeclaration;
    }
}
