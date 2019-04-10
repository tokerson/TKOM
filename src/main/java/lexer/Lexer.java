package lexer;

import data.TextPosition;
import data.Token;
import data.TokenType;
import data.Tokens;

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

    private char getNextCharacter() throws Exception{
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
        Token token;

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

        stringBuilder.append(current);
        char prev = current;
        current = getNextCharacter();

        if (Character.isDigit(prev)) {
            token = getNumberToken(stringBuilder);
        } else if (Character.isLetter(prev)) {
            token = getAlphabeticToken(stringBuilder);
        } else if (prev == '\"') {
            token = getStringToken(stringBuilder);
        } else {

            if (prev == '\n') {
                return getNextToken();
            }
            //checks whether it is a comment or not
            if (current == '/' && prev == '/') {
                int line = textPosition.getLineNumber();
                //skips to next line or finds the end of the file if current equals End of line
                while (line == textPosition.getLineNumber() && current != EOF) {
                    current = getNextCharacter();
                }
                return getNextToken();
            } else {
                token = getOperatorToken(stringBuilder);
            }
        }

        return token;
    }


    private Token getStringToken(StringBuilder stringBuilder) throws Exception {

        while (current != '\"' && current != EOF) {
            stringBuilder.append(current);
            current = getNextCharacter();
            //checking if there is a \" inside a string. If yes then we have to append it to string and keep on getting new chars
            if( current == '\\'){
                stringBuilder.append(current);
                current = getNextCharacter();
                if( current == '\"'){
                    stringBuilder.append(current);
                    current = getNextCharacter();
                }
            }
        }

        if (current == '\"') {
            stringBuilder.append(current);
            current = getNextCharacter();

            return new Token(stringBuilder.toString(), TokenType.STRING, textPosition);
        }

        throw new LexerException(textPosition);
//        return new Token(stringBuilder.toString(), TokenType.UNDEFINED);
    }

    private Token getOperatorToken(StringBuilder stringBuilder) throws Exception {

        if (Tokens.OPERATORS.containsKey(stringBuilder.toString() + current)) {
            stringBuilder.append(current);
            current = getNextCharacter();
        }


        final TokenType tokenType = Tokens.OPERATORS.get(stringBuilder.toString());

        if (tokenType != null) {
            return new Token(stringBuilder.toString(), tokenType, textPosition);
        } else if (!inputStreamReader.ready()) {
            return new Token(stringBuilder.toString(), TokenType.END, textPosition);
        }

        System.out.println(stringBuilder.toString());
        throw new LexerException(textPosition);
//        return new Token(stringBuilder.toString(), TokenType.UNDEFINED);

    }


    private Token getAlphabeticToken(StringBuilder stringBuilder) throws Exception {

        while (Character.isLetterOrDigit(current)) {
            stringBuilder.append(current);
            current = getNextCharacter();
        }

        if (Tokens.KEYWORDS.containsKey(stringBuilder.toString())) {
            return new Token(stringBuilder.toString(), Tokens.KEYWORDS.get(stringBuilder.toString()), textPosition);
        }

        return new Token(stringBuilder.toString(), TokenType.IDENTIFIER, textPosition);
    }

    private Token getNumberToken(StringBuilder stringBuilder) throws Exception {

        while (Character.isDigit(current)) {
            stringBuilder.append(current);
            current = getNextCharacter();
        }

        if (current == '.') {
            do {
                stringBuilder.append(current);
                current = getNextCharacter();
            } while (Character.isDigit(current));

            return new Token(stringBuilder.toString(), TokenType.DOUBLE, textPosition);
        }

        return new Token(stringBuilder.toString(), TokenType.INTEGER, textPosition);
    }

    private Boolean shouldReadAnotherCharacter() throws IOException {
        return inputStreamReader.ready() && current == ' ';
    }

    private Boolean isWhiteCharacter(char c){
        return c == ' ' || c == EOF || c == '\n';
    }

}
