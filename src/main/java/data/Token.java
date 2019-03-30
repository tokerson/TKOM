package data;

public class Token {
    private String content;
    private TokenType tokenType;

    public Token(String content, TokenType tokenType) {
        this.content = content;
        this.tokenType = tokenType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
