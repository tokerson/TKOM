package lexer;

import model.Token.TextPosition;

public class LexerException extends Exception {
    private TextPosition textPosition;

    public LexerException(TextPosition textPosition) {
        this.textPosition = textPosition;
    }

    public String toString(){
        return String.format("Lexer exception: Unexpected token at line %d and char %d",
                textPosition.getLineNumber(), textPosition.getCharacterNumber());
    }

    public TextPosition getTextPosition() {
        return textPosition;
    }

}
