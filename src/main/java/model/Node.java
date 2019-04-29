package model;

public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        Call,
        Condition,
        Integer,
        Double,
        Expression,
        FunctionDeclaration,
        FunctionAssignment,
        IfStatement,
        Program,
        ReturnStatement,
        BodyBlock,
        VarDeclaration,
        Literal,
        Variable,
        WhileStatement
    }

    public abstract Type getType();

    public abstract String toString();
}