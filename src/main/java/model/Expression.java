package model;

import model.Token.TokenType;
import semcheck.MyRunTimeException;

import java.util.LinkedList;
import java.util.List;

public class Expression extends Node implements Executable {

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
        Literal result = (Literal) literal;

        if (operations.size() > 0) {
            int i = 0;
            for (TokenType operation : operations) {
                Node operandNode = operands.get(++i);
                if (operandNode instanceof Executable) {
                    Executable operand =  ((Executable) operandNode).execute(scope);

                    switch (operation) {
                        case ADD_OPERATOR:
                            result = (Literal) ((Literal) literal).add(operand);
                            break;
                        case SUBSTRACT_OPERATOR:
                            result = (Literal) ((Literal) literal).substract(operand);
                            break;
                        case MULTIPLY_OPERATOR:
                            result = (Literal) ((Literal) literal).multiply(operand);
                            break;
                        case DIVIDE_OPERATOR:
                            result = (Literal) ((Literal) literal).divide(operand);
                            break;
                    }
                }
            }
        }

        return result;
    }

}
