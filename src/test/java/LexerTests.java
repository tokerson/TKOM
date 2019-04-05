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
        String text = "x";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be x", "x", token.getContent());
        assertEquals("token's type should be IDENTIFIER", TokenType.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void isRecognizingAddOperator() {
        String text = "+";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be + ", "+", token.getContent());
        assertEquals("token's type should be ADD_OPERATOR", TokenType.ADD_OPERATOR, token.getTokenType());
//        assertEquals(Tokens.OPERATORS.get().toString(), token.getTokenType().toString());
    }

    @Test
    public void isRecognizingInteger() {
        String text = "123";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123", "123", token.getContent());
        assertEquals("token's type should be INTEGER", TokenType.INTEGER, token.getTokenType());
//        assertEquals(Tokens.OPERATORS.get().toString(), token.getTokenType().toString());
    }

    @Test
    public void isRecognizingDouble() {
        String text = "123.4";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123.4", "123.4", token.getContent());
        assertEquals("token's type should be DOUBLE", TokenType.DOUBLE, token.getTokenType());
//        assertEquals(Tokens.OPERATORS.get().toString(), token.getTokenType().toString());
    }


    @Test
    public void isRecognizingIdentifierFollowedAndBegginingWithWhiteSpaces() {
        String text = "   x   ";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be x", "x", token.getContent());
        assertEquals("token's type should be IDENTIFIER", TokenType.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void isRecognizingIntegerFollowedAndBegginingWithWhiteSpaces() {
        String text = "   123  ";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123", "123", token.getContent());
        assertEquals("token's type should be INTEGER", TokenType.INTEGER, token.getTokenType());
    }

    @Test
    public void isNotRecognizingDoubleFollowedAndBegginingWithWhiteSpaces() {
        String text = "   123. 4  ";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123.", "123.", token.getContent());
        assertEquals("token's type should be DOUBLE", TokenType.DOUBLE, token.getTokenType());
    }

    @Test
    public void isIgnoringComments() {
        String text = "//comment  ";
        Token token = getTokenFromString(text);
        assertEquals("There should be no token", TokenType.END, token.getTokenType());
    }

    @Test
    public void isIdentifiyingTokenInNextLineAfterComment() {
        String text = "//comment  \ndef";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be def", "def", token.getContent());
        assertEquals("token's type should be FUNCTION_DECL", TokenType.FUNCTION_DECL, token.getTokenType());
    }

    @Test
    public void isIdentifiyingStrings() {
        String text = "\"hello world\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello world\"", "\"hello world\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningUndefinedWhenStringIsNotClosed() {
        String text = "\"hello world";
        Token token = getTokenFromString(text);
        assertEquals("token's type should be UNDEFINED", TokenType.UNDEFINED, token.getTokenType());
    }

    @Test
    public void isReturningProperMulitLineString() {
        String text = "\"hello \n\n world\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello \n\n world\"", "\"hello \n\n world\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningStringWithQuotesInside() {
        String text = "\"hello \\\"world\\\"\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello \\\"world\\\"\"", "\"hello \\\"world\\\"\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningStringWithBackslashesInside() {
        String text = "\"hello \\world\\\\\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello \\world\\\\\"", "\"hello \\world\\\\\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningEndTokenWhenTheFileIsEmpty() {
        String text = "";
        Token token = getTokenFromString(text);
        assertEquals("token's type should be END", TokenType.END, token.getTokenType());
    }

    @Test
    public void isReturningEndTokenWhenTheFileIsFullOfNewLines() {
        String text = "\n\n\n";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"\"", "", token.getContent());
        assertEquals("token's type should be END", TokenType.END, token.getTokenType());
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
