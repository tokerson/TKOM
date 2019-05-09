package model;

import model.Token.TokenType;

import java.util.LinkedList;
import java.util.List;

public class Expression extends Node {

    protected List<TokenType> operations = new LinkedList<>();
    protected List<Node> operands = new LinkedList<>();

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }

    public Type getType() {
        return Type.Expression;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("( ");

        if(operands.size() > 0) {
            stringBuilder.append(operands.get(0).toString());
        }

        for(int i = 1; i < operands.size(); i++) {
            stringBuilder.append(operations.get(i - 1).toString());
            stringBuilder.append(operands.get(i).toString());
        }

        stringBuilder.append(" )");

        return stringBuilder.toString();
    }

    public List<Node> getOperands() {
        return operands;
    }

    public List<TokenType> getOperations() {
        return operations;
    }
}
