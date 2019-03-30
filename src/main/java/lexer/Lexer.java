package lexer;

import data.TextPosition;
import data.Token;
import data.TokenType;
import data.Tokens;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lexer {

    private InputStreamReader inputStreamReader;
    private char current; //current character from stream
    private TextPosition textPosition;

    public Lexer(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
        this.textPosition = new TextPosition();
    }

    private char getNextCharacter() throws Exception {
        char nextCharacter = 0;

        try {
            nextCharacter = (char) inputStreamReader.read();
            if( nextCharacter == '\n') {
                this.textPosition.setToNextLine(); //we reached the end of line so we want to get a character from another line
                return getNextCharacter();
            }
            else{
                this.textPosition.incrementCharacterNumber();
            }
        } catch (IOException e) {
            throw new Exception(String.format("[ERROR] Error while reading next character at line %d and char %d",
                                                textPosition.getLineNumber(), textPosition.getLineNumber()));
        }

        return nextCharacter;
    }

    public Token getNextToken() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Token token = null;

        if (current == ' ' || current == 0) {
            current = getNextCharacter();

            while (shouldReadAnotherCharacter()) {
                current = getNextCharacter();
            }

            //is still a whitespace after that while loop
            if( current == ' ') {
                return new Token(stringBuilder.toString(), TokenType.UNDEFINED);
            }
        }

        stringBuilder.append(current);

        if(Character.isDigit(current)) {
            token = getNumberToken(stringBuilder);
        }
        else if(Character.isLetter(current)) {
            token = getAlphabeticToken(stringBuilder);
        } else {
            token = getOperatorToken(stringBuilder);
        }

        return token;
    }

    private Token getOperatorToken(StringBuilder stringBuilder) throws Exception {

        current = getNextCharacter();
        if(Tokens.OPERATORS.containsKey(stringBuilder.toString() + current)) {
            stringBuilder.append(current);
            current = getNextCharacter();
        }

        final TokenType tokenType = Tokens.OPERATORS.get(stringBuilder.toString());

        if(!inputStreamReader.ready()){
            return new Token(stringBuilder.toString(), TokenType.END);
        } else if(tokenType != null) {
            return new Token(stringBuilder.toString(), tokenType);
        }

        return new Token(stringBuilder.toString(), TokenType.UNDEFINED);
//        switch (current) {
//            case '=':
//                break;
//
//                //Single chars operators
//            case '+':
//                break;
//            case '-':
//                break;
//            case '*':
//                break;
//            case '/':
//                break;
//            case ',':
//                break;
//            case ';':
//                break;
//            case '[':
//                break;
//            case ']':
//                break;
//            case '(':
//                break;
//            case ')':
//                break;
//            default:
//                current = getNextCharacter();
//                return new Token(stringBuilder.toString(), TokenType.UNDEFINED);
//        }
    }


    private Token getAlphabeticToken(StringBuilder stringBuilder) throws Exception {
        current = getNextCharacter();

        while( Character.isLetterOrDigit( current )){
            stringBuilder.append(current);
            current = getNextCharacter();
        }

        if(Tokens.KEYWORDS.containsKey(stringBuilder.toString())){
            return new Token(stringBuilder.toString(), Tokens.KEYWORDS.get(stringBuilder.toString()));
        }

        return new Token(stringBuilder.toString(), TokenType.IDENTIFIER );
    }

    private Token getNumberToken(StringBuilder stringBuilder) throws Exception {

        current = getNextCharacter();

        while(Character.isDigit(current)){
            stringBuilder.append(current);
            current = getNextCharacter();
        }

        return new Token(stringBuilder.toString(), TokenType.NUMBER);
    }

    private boolean shouldReadAnotherCharacter() throws IOException {
        return inputStreamReader.ready() && current == ' ';
    }



}
