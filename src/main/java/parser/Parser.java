package parser;

import model.*;
import model.MyInteger;
import model.Program.Program;
import model.Token.Token;
import lexer.Lexer;
import model.Token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final Lexer lexer;
    private Token token;
    private Program program;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }


    public Program parse() throws Exception {
        program = new Program();
        Node parsedStatement = null;
        token = lexer.getNextToken();

        while ((parsedStatement = parseInstruction()) != null) {
            program.add(parsedStatement);
        }

        return program;
    }

    private Node parseInstruction() throws Exception {

        switch (token.getTokenType()) {
            case END:
                return null;
            case FUNCTION_DECL:
                accept(TokenType.FUNCTION_DECL);
                return parseFunctionDeclaration();
            case SEMICOLON:
                accept(TokenType.SEMICOLON);
                return parseInstruction();
            case IF:
                return parseIf();
            default:
                throw new ParserException(token, new TokenType[]{TokenType.END, TokenType.FUNCTION_DECL, TokenType.IF});
        }
    }

    private Node parseFunctionDeclaration() throws Exception {
        FunctionDeclaration functionDeclaration;
        TokenType type = parseReturnedType();

        String identifier = token.getContent();
        accept(TokenType.IDENTIFIER);

        switch (token.getTokenType()) {
            case PARENTHESIS_OPEN:
                accept(TokenType.PARENTHESIS_OPEN);
                functionDeclaration = new FunctionDeclaration(identifier, type, parseFunctionParameters(), parseFunctionBody(type));
                return functionDeclaration;
            case ASSIGN_OPERATOR:
                accept(TokenType.ASSIGN_OPERATOR);
                return parseFunctionAssignment(identifier, type);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.ASSIGN_OPERATOR, TokenType.PARENTHESIS_OPEN});
        }

    }

    private TokenType parseReturnedType() throws Exception {
        TokenType type = token.getTokenType();
        switch (token.getTokenType()) {
            case INT_TYPE:
                accept(TokenType.INT_TYPE);
                break;
            case DOUBLE_TYPE:
                accept(TokenType.DOUBLE_TYPE);
                break;
            case VOID:
                accept(TokenType.VOID);
                break;
            default:
                throw new ParserException(token, new TokenType[]{TokenType.INT_TYPE, TokenType.DOUBLE_TYPE, TokenType.VOID});
        }

        return type;
    }

    private BodyBlock parseFunctionBody(TokenType functionType) throws Exception {
        BodyBlock bodyBlock = new BodyBlock();
        boolean parsedReturn = false;

        accept(TokenType.BRACKET_OPEN);
        boolean endOfBlock = false;
        while (!endOfBlock) {
            switch (token.getTokenType()) {
                case IF:
                    bodyBlock.addInstruction(parseIf());
                    break;
                case RETURN:
                    if (functionType != TokenType.VOID) {
                        bodyBlock.addInstruction(parseReturn());
                        parsedReturn = true;
                    } else
                        throw new Exception("Unexpected return statement at line " + token.getTextPosition().getLineNumber() + " and char " + token.getTextPosition().getCharacterNumber());
                    break;
                case IDENTIFIER:
                    bodyBlock.addInstruction(parseFunctionCall());
                    accept(TokenType.SEMICOLON);
                    break;
                case FUNCTION_DECL:
                    accept(TokenType.FUNCTION_DECL);
                    TokenType type = parseReturnedType();
                    String identifier = token.getContent();
                    accept(TokenType.IDENTIFIER);
                    accept(TokenType.ASSIGN_OPERATOR);
                    bodyBlock.addInstruction(parseFunctionAssignment(identifier, type));
                    break;
                default:
                    if (token.getTokenType().equals(TokenType.BRACKET_CLOSE)) {
                        endOfBlock = true;
                    } else {
                        throw new ParserException(token, new TokenType[]{
                                TokenType.IF, TokenType.RETURN, TokenType.IDENTIFIER, TokenType.BRACKET_CLOSE, TokenType.FUNCTION_DECL
                        });
                    }
                    break;
            }
        }
        if (functionType != TokenType.IF && functionType != TokenType.ELSIF && functionType != TokenType.ELSE && functionType != TokenType.VOID && !parsedReturn) {
            throw new ParserException(token, new TokenType[]{TokenType.RETURN});
        }
        accept(TokenType.BRACKET_CLOSE);
        return bodyBlock;
    }

    private Node parseFunctionCall() throws Exception {
        String identifier = token.getContent();
        FunctionCall functionCall = new FunctionCall(identifier);
        accept(TokenType.IDENTIFIER);

        if (token.getTokenType() != TokenType.PARENTHESIS_OPEN) {
            return functionCall;
        }

        accept(TokenType.PARENTHESIS_OPEN);
        functionCall.setArguments(parseFunctionArguments());
        accept(TokenType.PARENTHESIS_CLOSE);
        return functionCall;
    }

    private List<Node> parseFunctionArguments() throws Exception {
        List<Node> arguments = new ArrayList<>();
        while (token.getTokenType() != TokenType.PARENTHESIS_CLOSE) {
            switch (token.getTokenType()) {
                case IDENTIFIER:
                case INTEGER:
                case DOUBLE:
                case ADD_OPERATOR:
                case SUBSTRACT_OPERATOR:
                    arguments.add(parseExpression());
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{
                            TokenType.IDENTIFIER, TokenType.INTEGER, TokenType.DOUBLE, TokenType.PARENTHESIS_CLOSE
                    });
            }
            if (token.getTokenType().equals(TokenType.COMMA)) {
                accept(TokenType.COMMA);
            }
        }
        return arguments;
    }

    private Node parseReturn() throws Exception {

        accept(TokenType.RETURN);
        Return returnStatement = new Return(parseExpression());

        return returnStatement;
    }

    private Node parseIf() throws Exception {
        IfStatement ifStatement = new IfStatement();

        accept(TokenType.IF);
        accept(TokenType.PARENTHESIS_OPEN);
        ifStatement.setCondition(parseCondition());
        accept(TokenType.PARENTHESIS_CLOSE);
        ifStatement.setThenBlock(parseFunctionBody(TokenType.IF));
        boolean foundElse = false;
        while (!foundElse && (token.getTokenType() == TokenType.ELSE || token.getTokenType() == TokenType.ELSIF)) {
            switch (token.getTokenType()) {
                case ELSIF:
                    accept(TokenType.ELSIF);
                    accept(TokenType.PARENTHESIS_OPEN);
                    Condition condition = parseCondition();
                    accept(TokenType.PARENTHESIS_CLOSE);
                    ifStatement.addElseIf(condition, parseFunctionBody(TokenType.ELSIF));
                    break;
                case ELSE:
                    accept(TokenType.ELSE);
                    ifStatement.setElseBlock(parseFunctionBody(TokenType.ELSE));
                    foundElse = true;
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.ELSIF, TokenType.ELSE});
            }
        }
        if(foundElse && token.getTokenType() == TokenType.ELSE){
            throw new ParserException(token, " Two ELSE statements for one IF");
        }

        return ifStatement;
    }

    private Condition parseCondition() throws Exception {
        Condition condition = new Condition();

        condition.addOperand(parseAndCondition());
        while (token.getTokenType() == TokenType.OR) {
            accept(TokenType.OR);
            condition.addOperator(TokenType.OR);
            condition.addOperand(parseAndCondition());
        }
        return condition;
    }

    private Condition parseAndCondition() throws Exception {
        Condition condition = new Condition();

        condition.addOperand(parseEqualityCondition());
        while (token.getTokenType() == TokenType.AND) {
            accept(TokenType.AND);
            condition.addOperator(TokenType.AND);
            condition.addOperand(parseEqualityCondition());
        }
        return condition;
    }

    private Node parseEqualityCondition() throws Exception {
        Condition condition = new Condition();

        condition.addOperand(parseRelationalCondition());
        while (token.getTokenType() == TokenType.EQUALS_OPERATOR || token.getTokenType() == TokenType.NOT_EQUALS_OPERATOR) {
            TokenType currentTokenType = token.getTokenType();
            switch (token.getTokenType()) {
                case EQUALS_OPERATOR:
                    accept(TokenType.EQUALS_OPERATOR);
                    break;
                case NOT_EQUALS_OPERATOR:
                    accept(TokenType.NOT_EQUALS_OPERATOR);
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.EQUALS_OPERATOR, TokenType.NOT_EQUALS_OPERATOR});
            }
            condition.addOperator(currentTokenType);
            condition.addOperand(parseRelationalCondition());
        }
        return condition;
    }

    private Node parseRelationalCondition() throws Exception {
        Condition condition = new Condition();

        condition.addOperand(parsePrimaryExpression());
        while ( token.getTokenType() == TokenType.LESS_OPERATOR || token.getTokenType() == TokenType.LESS_EQUALS_OPERATOR ||
                token.getTokenType() == TokenType.GREATER_OPERATOR || token.getTokenType() == TokenType.GREATER_EQUALS_OPERATOR) {
            TokenType currentTokenType = token.getTokenType();
            switch (token.getTokenType()) {
                case LESS_OPERATOR:
                    accept(TokenType.LESS_OPERATOR);
                    break;
                case LESS_EQUALS_OPERATOR:
                    accept(TokenType.LESS_EQUALS_OPERATOR);
                    break;
                case GREATER_OPERATOR:
                    accept(TokenType.GREATER_OPERATOR);
                    break;
                case GREATER_EQUALS_OPERATOR:
                    accept(TokenType.GREATER_EQUALS_OPERATOR);
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.LESS_EQUALS_OPERATOR, TokenType.LESS_OPERATOR, TokenType.GREATER_OPERATOR, TokenType.GREATER_EQUALS_OPERATOR});
            }
            condition.addOperator(currentTokenType);
            condition.addOperand(parsePrimaryExpression());
        }
        return condition;
    }

    private ArrayList<Parameter> parseFunctionParameters() throws Exception {
        ArrayList<Parameter> parameters = new ArrayList<>();
        String name;

        while (token.getTokenType() != TokenType.PARENTHESIS_CLOSE) {
            switch (token.getTokenType()) {
                case INT_TYPE:
                    accept(TokenType.INT_TYPE);
                    accept(TokenType.PARAMETER_TYPE);
                    name = token.getContent();
                    accept(TokenType.IDENTIFIER);
                    parameters.add(new Parameter(TokenType.INT_TYPE, name));
                    break;
                case DOUBLE_TYPE:
                    accept(TokenType.DOUBLE_TYPE);
                    accept(TokenType.PARAMETER_TYPE);
                    name = token.getContent();
                    accept(TokenType.IDENTIFIER);
                    parameters.add(new Parameter(TokenType.DOUBLE_TYPE, name));
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.INT_TYPE, TokenType.DOUBLE_TYPE, TokenType.PARENTHESIS_CLOSE});
            }
            if (token.getTokenType().equals(TokenType.COMMA)) {
                accept(TokenType.COMMA);
            }
        }
        accept(TokenType.PARENTHESIS_CLOSE);
        return parameters;
    }

    private Node parseFunctionAssignment(String identifier, TokenType returnType) throws Exception {
        return new FunctionAssignment(identifier, parseExpression(), returnType);
    }

    private Expression parseExpression() throws Exception {
        Expression expression = new Expression();
        expression.addOperand(parseMultiplicativeExpression());

        while (tokenIs(TokenType.ADD_OPERATOR, TokenType.SUBSTRACT_OPERATOR, TokenType.SEMICOLON, TokenType.IDENTIFIER)) {
            switch (token.getTokenType()) {
                case ADD_OPERATOR:
                    accept(TokenType.ADD_OPERATOR);
                    expression.addOperator(TokenType.ADD_OPERATOR);
                    expression.addOperand(parseMultiplicativeExpression());
                    break;
                case SUBSTRACT_OPERATOR:
                    accept(TokenType.SUBSTRACT_OPERATOR);
                    expression.addOperator(TokenType.SUBSTRACT_OPERATOR);
                    expression.addOperand(parseMultiplicativeExpression());
                    break;
                case SEMICOLON:
                    accept(TokenType.SEMICOLON);
                    break;
                case END:
                    return null;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.ADD_OPERATOR, TokenType.SUBSTRACT_OPERATOR, TokenType.SEMICOLON});
            }
        }

        return expression;
    }

    private boolean tokenIs(TokenType... tokenTypes) {

        for (TokenType type : tokenTypes) {
            if (token.getTokenType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    private Node parseMultiplicativeExpression() throws Exception {
        Expression expression = new Expression();
        expression.addOperand(parsePrimaryExpression());

        while (tokenIs(TokenType.MULTIPLY_OPERATOR, TokenType.DIVIDE_OPERATOR)) {
            switch (token.getTokenType()) {
                case MULTIPLY_OPERATOR:
                    accept(TokenType.MULTIPLY_OPERATOR);
                    expression.addOperator(TokenType.MULTIPLY_OPERATOR);
                    expression.addOperand(parsePrimaryExpression());
                    break;
                case DIVIDE_OPERATOR:
                    accept(TokenType.DIVIDE_OPERATOR);
                    expression.addOperator(TokenType.DIVIDE_OPERATOR);
                    expression.addOperand(parsePrimaryExpression());
                    break;
                case SEMICOLON:
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.MULTIPLY_OPERATOR, TokenType.DIVIDE_OPERATOR});
            }
        }

        return expression;

    }

    private Node parsePrimaryExpression() throws Exception {
        Expression expression = new Expression();

        switch (token.getTokenType()) {
            case PARENTHESIS_OPEN:
                accept(TokenType.PARENTHESIS_OPEN);
                expression.addOperand(parseExpression());
                accept(TokenType.PARENTHESIS_CLOSE);
                return expression;
            case IDENTIFIER:
                expression.addOperand(parseFunctionCall());
                return expression;
            default:
                return parseLiteral();
        }
    }

    private Node parseLiteral() throws Exception {

        switch (token.getTokenType()) {
            case SUBSTRACT_OPERATOR:
                accept(TokenType.SUBSTRACT_OPERATOR);
                return parseNumber(-1);
            case ADD_OPERATOR:
                accept(TokenType.ADD_OPERATOR);
                return parseNumber(1);
            case INTEGER:
            case DOUBLE:
                return parseNumber(1);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.SUBSTRACT_OPERATOR, TokenType.ADD_OPERATOR,
                        TokenType.INTEGER, TokenType.DOUBLE
                });
        }
    }

    private Node parseNumber(int sign) throws Exception {
        switch (token.getTokenType()) {
            case INTEGER:
                int intVal = Integer.parseInt(token.getContent());
                accept(TokenType.INTEGER);
                return new MyInteger(intVal * sign);
            case DOUBLE:
                double doubleVal = Double.parseDouble(token.getContent());
                accept(TokenType.DOUBLE);
                return new MyDouble(doubleVal * sign);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.INTEGER, TokenType.DOUBLE});
        }
    }

    private boolean accept(TokenType tokenType) throws Exception {

        if (token.getTokenType() == tokenType) {
            token = lexer.getNextToken();
            return true;
        } else {
            throw new ParserException(token, new TokenType[]{tokenType});
        }
    }


}
