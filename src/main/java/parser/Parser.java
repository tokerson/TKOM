package parser;

import model.*;
import model.MyInteger;
import model.Program.Program;
import model.Token.Token;
import lexer.Lexer;
import model.Token.TokenType;

import java.util.ArrayList;

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
            default:
                throw new ParserException(token, new TokenType[]{TokenType.END, TokenType.FUNCTION_DECL});
        }
    }

    private Node parseFunctionDeclaration() throws Exception {
        String identifier = token.getContent();
        accept(TokenType.IDENTIFIER);
        FunctionDeclaration functionDeclaration;

        switch (token.getTokenType()) {
            case PARENTHESIS_OPEN:
                accept(TokenType.PARENTHESIS_OPEN);
                functionDeclaration = new FunctionDeclaration(identifier, parseFunctionParameters(), parseFunctionBody());
                return functionDeclaration;
            case ASSIGN_OPERATOR:
                accept(TokenType.ASSIGN_OPERATOR);
                return parseFunctionAssignment(identifier);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.ASSIGN_OPERATOR, TokenType.PARENTHESIS_OPEN});
        }

    }

    private BodyBlock parseFunctionBody() throws Exception {
        BodyBlock bodyBlock = new BodyBlock();

        accept(TokenType.BRACKET_OPEN);
        boolean endOfBlock = false;
        while (!endOfBlock) {
            switch (token.getTokenType()) {
                case IF:
                    bodyBlock.addInstruction(parseIf());
                    break;
                case RETURN:
                    bodyBlock.addInstruction(parseReturn());
                    break;
                case IDENTIFIER:
                    bodyBlock.addInstruction(parseFunctionCall());
                    break;
                case FUNCTION_DECL:
                    accept(TokenType.FUNCTION_DECL);
                    String identifier = token.getContent();
                    accept(TokenType.IDENTIFIER);
                    accept(TokenType.ASSIGN_OPERATOR);
                    bodyBlock.addInstruction(parseFunctionAssignment(identifier));
                    break;
                default:
                    if(token.getTokenType().equals(TokenType.BRACKET_CLOSE)){
                        endOfBlock = true;
                    } else {
                        throw new ParserException(token, new TokenType[]{
                           TokenType.IF, TokenType.RETURN, TokenType.IDENTIFIER, TokenType.BRACKET_CLOSE
                        });
                    }
                    break;
            }
        }
        accept(TokenType.BRACKET_CLOSE);
        return bodyBlock;
    }

    private Node parseFunctionCall() {
        return null;
    }

    private Node parseReturn() {
        return null;
    }

    private Node parseIf() {
        return null;
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
                    parameters.add(new Parameter("Int", name));
                    break;
                case DOUBLE_TYPE:
                    accept(TokenType.DOUBLE_TYPE);
                    accept(TokenType.PARAMETER_TYPE);
                    name = token.getContent();
                    accept(TokenType.IDENTIFIER);
                    parameters.add(new Parameter("Double", name));
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

    private Node parseFunctionAssignment(String identifier) throws Exception {
        return new FunctionAssignment(identifier, parseExpression());
    }

    private Expression parseExpression() throws Exception {
        Expression expression = new Expression();
        expression.addOperand(parseMultiplitcativeExpression());

        while (tokenIs(TokenType.ADD_OPERATOR, TokenType.SUBSTRACT_OPERATOR, TokenType.SEMICOLON)) {
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

    private Node parseMultiplitcativeExpression() throws Exception {
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
