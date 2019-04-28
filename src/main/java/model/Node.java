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
        StatementBlock,
        VarDeclaration,
        Literal,
        Variable,
        WhileStatement
    }

    public abstract Type getType();


}