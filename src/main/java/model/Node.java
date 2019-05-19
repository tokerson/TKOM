package model;

public abstract class Node {

    public enum Type {
        FunctionCall,
        Condition,
        Integer,
        Double,
        Expression,
        FunctionDeclaration,
        FunctionAssignment,
        IfStatement,
        Return,
        BodyBlock,
    }

    public abstract Type getType();

    public abstract String toString();
}