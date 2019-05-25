package model;

import model.Token.TokenType;
import semcheck.MyRunTimeException;

public class Condition extends Expression {
    private boolean isNegated = false;

    @Override
    public Literal execute(Scope scope) throws MyRunTimeException {
        Node firstOperand = operands.get(0);
        Executable literal = null;
        if (firstOperand instanceof Executable) {
            literal = ((Executable) firstOperand).execute(scope);
        } else return null;
        Literal result = (Literal) literal;

        if (operations.size() > 0) {
            int i = 0;
            for (TokenType operation : operations) {
                Node operandNode = operands.get(++i);
                if (operandNode instanceof Executable) {
                    Executable operand =  ((Executable) operandNode).execute(scope);

                    switch (operation) {
                        case OR:
//                            result = (Literal) ((Literal) literal).or(operand);
                            break;
                        case AND:
                            result = (Literal) ((Literal) literal).substract(operand);
                            break;
                        case EQUALS_OPERATOR:
                            result = (Literal) ((Literal) literal).isEqual((Literal) operand);
                            break;
                        case NOT_EQUALS_OPERATOR:
                            result = (Literal) ((Literal) literal).divide(operand);
                            break;
                        case GREATER_OPERATOR:
                            break;
                        case GREATER_EQUALS_OPERATOR:
                            break;
                        case LESS_OPERATOR:
                            break;
                        case LESS_EQUALS_OPERATOR:
                            break;
                    }
                }
            }
        }

        return result;
//        return null;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }
}
