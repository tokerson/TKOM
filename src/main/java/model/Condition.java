package model;

import model.Token.TokenType;
import program.MyRunTimeException;

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
                            result = (Literal) ((Literal) literal).or((Literal) operand);
                            break;
                        case AND:
                            result = (Literal) ((Literal) literal).and((Literal) operand);
                            break;
                        case EQUALS_OPERATOR:
                            result = (Literal) ((Literal) literal).isEqual((Literal) operand);
                            break;
                        case NOT_EQUALS_OPERATOR:
                            result = (Literal) ((Literal) literal).isNotEqual((Literal) operand);
                            break;
                        case GREATER_OPERATOR:
                            result = (Literal) ((Literal) literal).isGreaterThan((Literal) operand);
                            break;
                        case GREATER_EQUALS_OPERATOR:
                            result = (Literal) ((Literal) literal).isGreaterOrEqualThan((Literal) operand);
                            break;
                        case LESS_OPERATOR:
                            result = (Literal) ((Literal) literal).isLessThan((Literal) operand);
                            break;
                        case LESS_EQUALS_OPERATOR:
                            result = (Literal) ((Literal) literal).isLessOrEqualThan((Literal) operand);
                            break;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }
}
