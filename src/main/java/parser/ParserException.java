package parser;

import model.Token.TextPosition;
import model.Token.Token;
import model.Token.TokenType;

public class ParserException extends Exception{

    private final Token token;
    private final TokenType[] expected;

    public ParserException(Token token, TokenType[] expected) {
        this.token = token;
        this.expected = expected;
    }

    @Override
    public String getMessage(){
        StringBuilder   stringBuilder = new StringBuilder("Error at line: ");
        stringBuilder.append(token.getTextPosition().getLineNumber());
        stringBuilder.append(" and char: ");
        stringBuilder.append(token.getTextPosition().getCharacterNumber());
        stringBuilder.append("\nExpected ");
        for (TokenType type: expected) {
            stringBuilder.append(type);
            stringBuilder.append(", ");
        }
        stringBuilder.append("but found ");
        stringBuilder.append(token.getTokenType());
        stringBuilder.append(".");

        return stringBuilder.toString();
    }

}
