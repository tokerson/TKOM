package model;

import model.Token.TokenType;
import program.MyRunTimeException;

public class Condition extends Expression {

    @Override
    public Literal execute(Scope scope) throws MyRunTimeException {
        Node firstOperand = operands.get(0);
        Executable literal;
        if (firstOperand instanceof Executable) {
            literal = ((Executable) firstOperand).execute(scope);
        } else return null;

        return evaluateExpression((Literal) literal, scope);
    }

    @Override
    Literal handleOperation(Literal result, TokenType operation, Executable operand) throws MyRunTimeException {
        switch (operation) {
            case OR:
                result = (Literal) result.or((Literal) operand);
                break;
            case AND:
                result = (Literal) result.and((Literal) operand);
                break;
            case EQUALS_OPERATOR:
                result = (Literal) result.isEqual((Literal) operand);
                break;
            case NOT_EQUALS_OPERATOR:
                result = (Literal) result.isNotEqual((Literal) operand);
                break;
            case GREATER_OPERATOR:
                result = (Literal) result.isGreaterThan((Literal) operand);
                break;
            case GREATER_EQUALS_OPERATOR:
                result = (Literal) result.isGreaterOrEqualThan((Literal) operand);
                break;
            case LESS_OPERATOR:
                result = (Literal) result.isLessThan((Literal) operand);
                break;
            case LESS_EQUALS_OPERATOR:
                result = (Literal) result.isLessOrEqualThan((Literal) operand);
                break;
        }
        return result;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }
}
