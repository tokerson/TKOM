package model;

public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        FunctionCall,
        Condition,
        Integer,
        Double,
        Expression,
        FunctionDeclaration,
        FunctionAssignment,
        IfStatement,
        Program,
        Return,
        BodyBlock,
        VarDeclaration,
        Literal,
        Variable,
        WhileStatement
    }

    public abstract Type getType();

    public abstract String toString();
}