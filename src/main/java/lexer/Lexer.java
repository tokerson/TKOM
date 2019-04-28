package lexer;

import model.Token.TextPosition;
import model.Token.Token;
import model.Token.TokenType;
import model.Token.Tokens;

import java.io.IOException;
import java.io.InputStreamReader;

public class Lexer {

    private InputStreamReader inputStreamReader;
    private char current; //current character from stream
    private TextPosition textPosition;
    private final char EOF = 0; // end of file

    public Lexer(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
        this.textPosition = new TextPosition();
    }

    private char getNextCharacter() throws Exception {
        char nextCharacter = EOF;

        try {
            if (inputStreamReader.ready()) {
                nextCharacter = (char) inputStreamReader.read();
                if (nextCharacter == '\n') {
                    this.textPosition.setToNextLine(); //we reached the end of line so we want to get a character from another line
                } else {
                    this.textPosition.incrementCharacterNumber();
                }
            }
        } catch (IOException e) {
            throw new LexerException(textPosition);
        }

        return nextCharacter;
    }

    public Token getNextToken() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        if (isWhiteCharacter(current)) {
            current = getNextCharacter();

            while (shouldReadAnotherCharacter()) {
                current = getNextCharacter();
            }
            //is still a whitespace after that while loop
            if (current == EOF) {
                return new Token(stringBuilder.toString(), TokenType.END, textPosition);
            }
        }

        TextPosition tokenPosition = textPosition.clone();

        if (Character.isDigit(current)) {
            return getNumberToken(stringBuilder, tokenPosition);
        } else if (Character.isLetter(current)) {
            return getAlphabeticToken(stringBuilder, tokenPosition);
        } else if (current == '\"') {
            return getStringToken(stringBuilder, tokenPosition);
        } else if (current == '\n') {
            return getNextToken();
        } else {
            return getOperatorToken(stringBuilder, tokenPosition);
        }
    }


    private Token getStringToken(StringBuilder stringBuilder, TextPosition tokenPosition) throws Exception {

        current = getNextCharacter();

        while (current != '"' && current != EOF){
            stringBuilder.append(current);
            current = getNextCharacter();
            //checking if there is a \" inside a string. If yes then we have to append it to string and keep on getting new chars
            if (current == '\\') {
                System.out.println("ukośnik + " + textPosition.getCharacterNumber());
//                stringBuilder.append(current);
                current = getNextCharacter();
//                if(current != '"'){
//                    stringBuilder.append(current);
//                    current = getNextCharacter();
//                }
            }
        }

        if (current == '"') {
            current = getNextCharacter();

            return new Token(stringBuilder.toString(), TokenType.STRING, tokenPosition);
        }

        throw new LexerException(tokenPosition);
    }

    private Token getOperatorToken(StringBuilder stringBuilder, TextPosition tokenPosition) throws Exception {
        stringBuilder.append(current);
        current = getNextCharacter();

        if (Tokens.OPERATORS.containsKey(stringBuilder.toString() + current)) {
            stringBuilder.append(current);
            current = getNextCharacter();
        } else if ((stringBuilder.toString() + current).equals("//")) {
            int line = textPosition.getLineNumber();
            //skips to next line or finds the end of the file if current equals End of line
            while (line == textPosition.getLineNumber() && current != EOF) {
                current = getNextCharacter();
            }
            return getNextToken();
        }

        final TokenType tokenType = Tokens.OPERATORS.get(stringBuilder.toString());

        if (tokenType != null) {
            return new Token(stringBuilder.toString(), tokenType, tokenPosition);
        } else if (!inputStreamReader.ready()) {
            return new Token(stringBuilder.toString(), TokenType.END, tokenPosition);
        }

        throw new LexerException(tokenPosition);
    }


    private Token getAlphabeticToken(StringBuilder stringBuilder, TextPosition tokenPosition) throws Exception {

        do {
            stringBuilder.append(current);
            current = getNextCharacter();
        } while (Character.isLetterOrDigit(current));

        if (Tokens.KEYWORDS.containsKey(stringBuilder.toString())) {
            return new Token(stringBuilder.toString(), Tokens.KEYWORDS.get(stringBuilder.toString()), tokenPosition);
        }

        return new Token(stringBuilder.toString(), TokenType.IDENTIFIER, tokenPosition);
    }

    private Token getNumberToken(StringBuilder stringBuilder, TextPosition tokenPosition) throws Exception {

        do {
            stringBuilder.append(current);
            current = getNextCharacter();
        } while (Character.isDigit(current));

        if (current == '.') {
            do {
                stringBuilder.append(current);
                current = getNextCharacter();
            } while (Character.isDigit(current));

            return new Token(stringBuilder.toString(), TokenType.DOUBLE, tokenPosition);
        }

        return new Token(stringBuilder.toString(), TokenType.INTEGER, tokenPosition);
    }

    private Boolean shouldReadAnotherCharacter() throws IOException {
        return inputStreamReader.ready() && current == ' ';
    }

    private Boolean isWhiteCharacter(char c) {
        return c == ' ' || c == EOF || c == '\n';
    }

}
