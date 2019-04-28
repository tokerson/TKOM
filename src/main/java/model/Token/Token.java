package model.Token;

public class Token {
    private String content;
    private TokenType tokenType;
    private TextPosition textPosition;

    public Token(String content, TokenType tokenType, TextPosition textPosition) {
        this.content = content;
        this.tokenType = tokenType;
        this.textPosition = textPosition;
    }

    public String getContent() {
        return content;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public TextPosition getTextPosition() {
        return textPosition;
    }
}
