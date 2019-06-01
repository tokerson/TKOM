package model;

import model.Token.TokenType;
import program.MyRunTimeException;

import java.util.LinkedList;
import java.util.List;

public class Expression extends Node implements Executable {

    private List<TokenType> operations = new LinkedList<>();

    List<Node> operands = new LinkedList<>();

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (operands.size() > 0) {
            stringBuilder.append(operands.get(0).toString());
        }

        for (int i = 1; i < operands.size(); i++) {
            stringBuilder.append(operations.get(i - 1).toString());
            stringBuilder.append(operands.get(i).toString());
        }

        return stringBuilder.toString();
    }

    public List<Node> getOperands() {
        return operands;
    }

    public List<TokenType> getOperations() {
        return operations;
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        Node firstOperand = operands.get(0);
        Executable literal = null;
        if (firstOperand instanceof Executable) {
            literal = ((Executable) firstOperand).execute(scope);
        } else return null;
        if(literal instanceof Array){
            return literal;
        }
        return evaluateExpression((Literal) literal, scope);
    }

    Literal evaluateExpression(Literal literal, Scope scope) throws MyRunTimeException {
        Literal result =  literal;

        if (operations.size() > 0) {
            int i = 0;
            for (TokenType operation : operations) {
                Node operandNode = operands.get(++i);
                if (operandNode instanceof Executable) {
                    Executable operand =  ((Executable) operandNode).execute(scope);
                    result = this.handleOperation(result, operation, operand);
                }
            }
        }
        return result;
    }

    Literal handleOperation(Literal result, TokenType operation, Executable operand) throws MyRunTimeException {
        switch (operation) {
            case ADD_OPERATOR:
                result = (Literal) ((Literal) result).add((Literal) operand);
                break;
            case SUBSTRACT_OPERATOR:
                result = (Literal) ((Literal) result).substract((Literal) operand);
                break;
            case MULTIPLY_OPERATOR:
                result = (Literal) ((Literal) result).multiply((Literal) operand);
                break;
            case DIVIDE_OPERATOR:
                result = (Literal) ((Literal) result).divide((Literal) operand);
                break;
        }
        return result;
    }

}
