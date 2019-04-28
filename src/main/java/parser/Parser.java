package parser;

import model.*;
import model.MyInteger;
import model.Program.Program;
import model.Token.Token;
import lexer.Lexer;
import model.Token.TokenType;

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
            default:
                throw new ParserException(token, new TokenType[]{TokenType.END, TokenType.FUNCTION_DECL});

        }

    }

    private Node parseFunctionDeclaration() throws Exception {
        String identifier = token.getContent();
        accept(TokenType.IDENTIFIER);

        switch (token.getTokenType()) {
            case SEMICOLON:
                accept(TokenType.SEMICOLON);
                return new FunctionDeclaration(identifier);
            case ASSIGN_OPERATOR:
                accept(TokenType.ASSIGN_OPERATOR);
                return parseFunctionAssignment(identifier);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.SEMICOLON});
        }

    }

    private Node parseFunctionAssignment(String identifier) throws Exception {
        return new FunctionAssignment(identifier, parseExpression());
    }

    private Expression parseExpression() throws Exception {
        Expression expression = new Expression();
        expression.addOperand(parseMultiplitcativeExpression());

        switch (token.getTokenType()) {
            case ADD_OPERATOR:
                accept(TokenType.ADD_OPERATOR);
                expression.addOperator(TokenType.ADD_OPERATOR);
                expression.addOperand(parseMultiplitcativeExpression());
                break;
            case SUBSTRACT_OPERATOR:
                accept(TokenType.SUBSTRACT_OPERATOR);
                expression.addOperator(TokenType.SUBSTRACT_OPERATOR);
                expression.addOperand(parseMultiplitcativeExpression());
                break;
            case SEMICOLON:
                accept(TokenType.SEMICOLON);
                break;
            case END:
                return null;
            default:
                throw new ParserException(token, new TokenType[]{TokenType.ADD_OPERATOR, TokenType.SUBSTRACT_OPERATOR, TokenType.SEMICOLON});
        }

        return expression;
    }

    private Node parseMultiplitcativeExpression() throws Exception {
        Expression expression = new Expression();
        expression.addOperand(parsePrimaryExpression());

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

        return expression;

    }

    private Node parsePrimaryExpression() throws Exception {
        Expression expression;

        switch (token.getTokenType()) {
            case PARENTHESIS_OPEN:
                accept(TokenType.PARENTHESIS_OPEN);
                expression = parseExpression();
                accept(TokenType.PARENTHESIS_CLOSE);
                return expression;
            case IDENTIFIER:
                break;
            default:
                return parseLiteral();
        }
        return null;
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
                double doubleVal= Double.parseDouble(token.getContent());
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
