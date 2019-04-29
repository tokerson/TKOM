package model.Token;

import java.util.HashMap;

import static model.Token.TokenType.*;

public class Tokens {
    public static final HashMap<String, TokenType> KEYWORDS = new HashMap<String, TokenType>() {{
        put("def", FUNCTION_DECL);
        put("void", VOID);
        put("if", IF);
        put("elsif", ELSIF);
        put("else", ELSE);
        put("return", RETURN);
        put("Int", INT_TYPE);
        put("Double", DOUBLE_TYPE);
    }};

    public static final HashMap<String, TokenType> OPERATORS = new HashMap<String, TokenType>() {{
        put("=", ASSIGN_OPERATOR);
        put("==", EQUALS_OPERATOR);
        put("!=", NOT_EQUALS_OPERATOR);
        put(">=", GREATER_EQUALS_OPERATOR);
        put("=<", LESS_EQUALS_OPERATOR);
        put("<", LESS_OPERATOR);
        put(">", GREATER_OPERATOR);
        put("&&", AND);
        put("||", OR);
        put(",", COMMA);
        put(";", SEMICOLON);
        put("+", ADD_OPERATOR);
        put("-", SUBSTRACT_OPERATOR);
        put("*", MULTIPLY_OPERATOR);
        put("/", DIVIDE_OPERATOR);
        put("(", PARENTHESIS_OPEN);
        put(")", PARENTHESIS_CLOSE);
        put("{", BRACKET_OPEN);
        put("}", BRACKET_CLOSE);
        put("\"", QUOTATION);
        put(":", PARAMETER_TYPE);
        put("[", ARRAY_OPEN);
        put("]", ARRAY_CLOSE);
    }};

}
