package data;

import java.util.HashMap;

import static data.TokenType.*;

public class Tokens {
    public static final HashMap<String, TokenType> KEYWORDS = new HashMap<String, TokenType>() {{
        put("def", FUNCTION);
    }};

    public static final HashMap<String, TokenType> OPERATORS = new HashMap<String, TokenType>() {{
        put("=", ASSIGN_OPERATOR);
        put("==", EQUALS_OPERATOR);
        put(">=", GREATER_EQUALS_OPERATOR);
        put("=<", LESS_EQUALS_OPERATOR);
        put("&&", AND);
        put("||", OR);
        put(",", COMMA);
        put(";", SEMICOLON);
        put("+", ADD_OPERATOR);
        put("-", SUBSTRACT_OPERATOR);
        put("*", MULTIPLY_OPERATOR);
        put("/", DIVIDE_OPERATOR);
    }};

}
