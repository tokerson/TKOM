package model;

public class Return extends Node {

    private Node returnStatement;

    public Return(Node returnStatement) {
        this.returnStatement = returnStatement;
    }

    @Override
    public Type getType() {
        return Type.Return;
    }

    @Override
    public String toString() {
        return "return " + returnStatement;
    }
}
