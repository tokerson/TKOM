package model;

import model.Token.TokenType;

import java.util.LinkedList;
import java.util.List;

public class Expression extends Node {
    public List<TokenType> operations = new LinkedList<>();

    public List<Node> operands = new LinkedList<>();

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }

    public Type getType() {
        return Type.Expression;
    }

    public List<Node> getOperands() {
        return operands;
    }

    public List<TokenType> getOperations() {
        return operations;
    }
}
