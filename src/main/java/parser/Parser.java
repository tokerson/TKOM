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
    private Scope scope;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Program parse() throws Exception {
        program = new Program();
        scope = program.getScope();
        Node parsedStatement = null;
        token = lexer.getNextToken();

        while ((parsedStatement = parseInstruction(scope)) != null) {
            program.add(parsedStatement);
        }

        return program;
    }

    private Node parseInstruction(Scope scope) throws Exception {

        switch (token.getTokenType()) {
            case END:
                return null;
            case FUNCTION_DECL:
                accept(TokenType.FUNCTION_DECL);
                return parseFunctionDeclaration(scope);
            case IDENTIFIER:
                return parseFunctionCall();
            case SEMICOLON:
                accept(TokenType.SEMICOLON);
                return parseInstruction(scope);
            case IF:
                return parseIf(scope);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.END, TokenType.FUNCTION_DECL, TokenType.IF});
        }
    }

    private Node parseFunctionDeclaration(Scope scope) throws Exception {
        FunctionDeclaration functionDeclaration;
        MyType type = parseReturnedType();

        String identifier = token.getContent();
        addToScope(scope, identifier, type);
        accept(TokenType.IDENTIFIER);

        switch (token.getTokenType()) {
            case PARENTHESIS_OPEN:
                accept(TokenType.PARENTHESIS_OPEN);
                functionDeclaration = new FunctionDeclaration(identifier, type);
                functionDeclaration.setParameters(parseFunctionParameters(functionDeclaration.getScope()));
                functionDeclaration.setBodyBlock(parseFunctionBody(functionDeclaration.getScope(),type));
                functionDeclaration.setParentScope(scope);
                return functionDeclaration;
            case ASSIGN_OPERATOR:
                accept(TokenType.ASSIGN_OPERATOR);
                return parseFunctionAssignment(identifier, type);
            default:
                throw new ParserException(token, new TokenType[]{TokenType.ASSIGN_OPERATOR, TokenType.PARENTHESIS_OPEN});
        }

    }

    private void addToScope(Scope scope, String identifier, MyType myType) throws Exception {
        if(!scope.addToScope(identifier, myType)){
            throw new Exception("Redefinition of function " + identifier + " at line: " +token.getTextPosition().getLineNumber() + " and char: " + token.getTextPosition().getCharacterNumber() + " within same scope");
        }
    }

    private MyType parseReturnedType() throws Exception {
        TokenType type;
        MyType myType;
        boolean isArray = false;
        switch (token.getTokenType()) {
            case ARRAY_OPEN:
                accept(token.getTokenType());
                accept(TokenType.ARRAY_CLOSE);
                isArray = true;
                if(tokenIs(TokenType.INT_TYPE, TokenType.DOUBLE_TYPE)){
                    type = token.getTokenType();
                    accept(token.getTokenType());
                } else throw new ParserException(token, new TokenType[]{TokenType.INT_TYPE, TokenType.DOUBLE_TYPE});
                break;
            case INT_TYPE:
            case DOUBLE_TYPE:
                type = token.getTokenType();
                accept(type);
                break;
            default:
                throw new ParserException(token, new TokenType[]{TokenType.INT_TYPE, TokenType.DOUBLE_TYPE, TokenType.ARRAY_OPEN});
        }

        myType = new MyType(isArray, type);
        return myType;
    }

    private BodyBlock  parseFunctionBody(Scope scope, MyType functionType) throws Exception {
        BodyBlock bodyBlock = new BodyBlock();
        boolean parsedReturn = false;

        accept(TokenType.BRACKET_OPEN);
        boolean endOfBlock = false;
        while (!endOfBlock) {
            switch (token.getTokenType()) {
                case IF:
                    bodyBlock.addInstruction(parseIf(scope));
                    break;
                case RETURN:
                    bodyBlock.addInstruction(parseReturn());
                    parsedReturn = true;
                    break;
                case IDENTIFIER:
                    bodyBlock.addInstruction(parseFunctionCall());
                    accept(TokenType.SEMICOLON);
                    break;
                case FUNCTION_DECL:
                    accept(TokenType.FUNCTION_DECL);
                    MyType type = parseReturnedType();
                    String identifier = token.getContent();
                    addToScope(scope,identifier, type);
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
        if (functionType.getType() != TokenType.IF && functionType.getType() != TokenType.ELSIF && functionType.getType() != TokenType.ELSE && !parsedReturn) {
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

    private List<Executable> parseFunctionArguments() throws Exception {
        List<Executable> arguments = new ArrayList<>();
        while (token.getTokenType() != TokenType.PARENTHESIS_CLOSE) {
            switch (token.getTokenType()) {
                case IDENTIFIER:
                case INTEGER:
                case DOUBLE:
                case ADD_OPERATOR:
                case SUBSTRACT_OPERATOR:
                case PARENTHESIS_OPEN:
                    arguments.add(parseExpression());
                    break;
                case ARRAY_OPEN:
                    arguments.add(parseArrayInit(new MyType(true,TokenType.UNDEFINED)));
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{
                            TokenType.IDENTIFIER, TokenType.INTEGER, TokenType.DOUBLE, TokenType.PARENTHESIS_CLOSE, TokenType.PARENTHESIS_OPEN, TokenType.ARRAY_OPEN
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

    private Node parseIf(Scope scope) throws Exception {
        IfStatement ifStatement = new IfStatement();

        accept(TokenType.IF);
        accept(TokenType.PARENTHESIS_OPEN);
        ifStatement.setCondition(parseCondition());
        accept(TokenType.PARENTHESIS_CLOSE);
        ifStatement.setThenBlock(parseFunctionBody(scope, new MyType(false,TokenType.IF)));
        boolean foundElse = false;
        while (!foundElse && (token.getTokenType() == TokenType.ELSE || token.getTokenType() == TokenType.ELSIF)) {
            switch (token.getTokenType()) {
                case ELSIF:
                    accept(TokenType.ELSIF);
                    accept(TokenType.PARENTHESIS_OPEN);
                    Condition condition = parseCondition();
                    accept(TokenType.PARENTHESIS_CLOSE);
                    ifStatement.addElseIf(condition, parseFunctionBody(scope, new MyType(false,TokenType.ELSIF)));
                    break;
                case ELSE:
                    accept(TokenType.ELSE);
                    ifStatement.setElseBlock(parseFunctionBody(scope, new MyType(false,TokenType.ELSE)));
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

        condition.addOperand(parseExpression());
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
            condition.addOperand(parseExpression());
        }
        return condition;
    }

    private ArrayList<Parameter> parseFunctionParameters(Scope scope) throws Exception {
        ArrayList<Parameter> parameters = new ArrayList<>();

        while (token.getTokenType() != TokenType.PARENTHESIS_CLOSE) {
            switch (token.getTokenType()) {
                case INT_TYPE:
                case DOUBLE_TYPE:
                case ARRAY_OPEN:
                    parameters.add(parseOneParameter(scope,token.getTokenType()));
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.INT_TYPE, TokenType.DOUBLE_TYPE, TokenType.ARRAY_OPEN, TokenType.PARENTHESIS_CLOSE});
            }
            if (token.getTokenType().equals(TokenType.COMMA)) {
                accept(TokenType.COMMA);
            }
        }
        accept(TokenType.PARENTHESIS_CLOSE);
        return parameters;
    }

    private Parameter parseOneParameter(Scope scope, TokenType tokenType) throws Exception {
        String name;
        boolean array = false;
        accept(tokenType);
        if(tokenType == TokenType.ARRAY_OPEN) {
            accept(TokenType.ARRAY_CLOSE);
            switch (token.getTokenType()) {
                case INT_TYPE:
                case DOUBLE_TYPE:
                    tokenType = token.getTokenType();
                    accept(tokenType);
                    array = true;
                    break;
                default:
                    throw new ParserException(token, new TokenType[]{TokenType.INT_TYPE, TokenType.DOUBLE_TYPE});
            }
        }
        accept(TokenType.PARAMETER_TYPE);
        name = token.getContent();
        MyType type = new MyType(array,tokenType);
        addToScope(scope,name,type);
        accept(TokenType.IDENTIFIER);
        return new Parameter(type, name);
    }

    private Node parseFunctionAssignment(String identifier, MyType returnType) throws Exception {
        Expression expression;

        if(token.getTokenType() == TokenType.ARRAY_OPEN){
            expression = new Expression();
            expression.addOperand(parseArrayInit(returnType));
            returnType.setArray(true);
        } else {
            expression = parseExpression();
        }

        return new FunctionAssignment(identifier, expression, returnType);
    }

    private Expression parseExpression() throws Exception {
        Expression expression = new Expression();

        expression.addOperand(parseMultiplicativeExpression());

        while (tokenIs(TokenType.ADD_OPERATOR, TokenType.SUBSTRACT_OPERATOR, TokenType.SEMICOLON, TokenType.IDENTIFIER)) {
            switch (token.getTokenType()) {
                case ADD_OPERATOR:
                case SUBSTRACT_OPERATOR:
                    TokenType tokenType = token.getTokenType();
                    accept(tokenType);
                    expression.addOperator(tokenType);
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

    private Array parseArrayInit(MyType returnType) throws Exception {
        accept(TokenType.ARRAY_OPEN);
        Array array ;
        if(returnType.getType() == TokenType.UNDEFINED && token.getTokenType() != TokenType.ARRAY_CLOSE){
            if(tokenIs(TokenType.INTEGER, TokenType.DOUBLE)){
                array = new Array(token.getTokenType());
            } else {
                throw new ParserException(token, new TokenType[]{TokenType.INTEGER, TokenType.DOUBLE});
            }
        } else {
           array = new Array(returnType.getType());
        }
        while(tokenIs(TokenType.INTEGER, TokenType.DOUBLE)){
            array.addElement(parseLiteral());
            if(token.getTokenType() == TokenType.COMMA){
                accept(TokenType.COMMA);
            } else {
                break;
            }
        }
        accept(TokenType.ARRAY_CLOSE);
        return array;
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
                case DIVIDE_OPERATOR:
                    TokenType tokenType = token.getTokenType();
                    accept(tokenType);
                    expression.addOperator(tokenType);
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

    private Literal parseLiteral() throws Exception {

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

    private Literal parseNumber(int sign) throws Exception {
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
