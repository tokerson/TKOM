import data.Token;
import data.TokenType;
import data.Tokens;
import lexer.Lexer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;


public class LexerTests {

    private Lexer lexer;
    private InputStreamReader inputStreamReader;


    @Before
    public void before() {
        System.out.println("Setting up tests");
    }

    @Test
    public void isRecognizingIdentifier() {
        String id = "x";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be x", "x", token.getContent());
        assertEquals("token's type should be IDENTIFIER", TokenType.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void isRecognizingAddOperator() {
        String oper = "+";
        Token token = getTokenFromString(oper);
        assertEquals("token's content should be + ", "+", token.getContent());
        assertEquals("token's type should be ADD_OPERATOR", TokenType.ADD_OPERATOR, token.getTokenType());
//        assertEquals(Tokens.OPERATORS.get().toString(), token.getTokenType().toString());
    }

    @Test
    public void isRecognizingInteger() {
        String string = "123";
        Token token = getTokenFromString(string);
        assertEquals("token's content should be 123", "123", token.getContent());
        assertEquals("token's type should be INTEGER", TokenType.INTEGER, token.getTokenType());
//        assertEquals(Tokens.OPERATORS.get().toString(), token.getTokenType().toString());
    }

    @Test
    public void isRecognizingDouble() {
        String string = "123.4";
        Token token = getTokenFromString(string);
        assertEquals("token's content should be 123.4", "123.4", token.getContent());
        assertEquals("token's type should be DOUBLE", TokenType.DOUBLE, token.getTokenType());
//        assertEquals(Tokens.OPERATORS.get().toString(), token.getTokenType().toString());
    }


    @Test
    public void isRecognizingIdentifierFollowedAndBegginingWithWhiteSpaces() {
        String id = "   x   ";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be x", "x", token.getContent());
        assertEquals("token's type should be IDENTIFIER", TokenType.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void isRecognizingIntegerFollowedAndBegginingWithWhiteSpaces() {
        String id = "   123  ";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be 123", "123", token.getContent());
        assertEquals("token's type should be INTEGER", TokenType.INTEGER, token.getTokenType());
    }

    @Test
    public void isNotRecognizingDoubleFollowedAndBegginingWithWhiteSpaces() {
        String id = "   123. 4  ";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be 123.", "123.", token.getContent());
        assertEquals("token's type should be DOUBLE", TokenType.DOUBLE, token.getTokenType());
    }

    @Test
    public void isIgnoringComments() {
        String id = "//comment  ";
        Token token = getTokenFromString(id);
        assertEquals("There should be no token", TokenType.END, token.getTokenType());
    }

    @Test
    public void isIdentifiyingTokenInNextLineAfterComment() {
        String id = "//comment  \ndef";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be def", "def", token.getContent());
        assertEquals("token's type should be FUNCTION_DECL", TokenType.FUNCTION_DECL, token.getTokenType());
    }

    @Test
    public void isIdentifiyingStrings() {
        String id = "\"hello world\"";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be \"hello world\"", "\"hello world\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningUndefinedWhenStringIsNotClosed() {
        String id = "\"hello world";
        Token token = getTokenFromString(id);
        assertEquals("token's type should be UNDEFINED", TokenType.UNDEFINED, token.getTokenType());
    }

    @Test
    public void isReturningProperMulitLineString() {
        String id = "\"hello \n\n world\"";
        Token token = getTokenFromString(id);
        assertEquals("token's content should be \"hello \n\n world\"", "\"hello \n\n world\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }


    private Token getTokenFromString(String string) {
        lexer = new Lexer(convertStringToInputStreamReader(string));
        try {
            return lexer.getNextToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private InputStreamReader convertStringToInputStreamReader(String string) {
        return new InputStreamReader(new ByteArrayInputStream(string.getBytes()));
    }
}
